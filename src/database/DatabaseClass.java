package database;

import booking.Booking;
import booking.BookingClass;
import booking.BookingState;
import booking.IteratorOfTwoIterators;
import booking.exceptions.*;
import commands.Command;
import property.*;
import property.exceptions.NumGuestsExceedsCapacityException;
import property.exceptions.PropertyAlreadyExistException;
import property.exceptions.PropertyDoesNotExistException;
import users.*;
import users.exceptions.InvalidUserTypeException;
import users.exceptions.NoGlobeTrotterException;
import users.exceptions.UserAlreadyExistException;
import users.exceptions.UserDoesNotExistException;

import java.time.LocalDate;
import java.util.*;

/**
 * Database class which communicates with Main class
 * Stores information about all properties and users.
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class DatabaseClass implements Database {

    private final int MAX_NUM_GUESTS = 15;
    private final String BOOKING_ID_FORMAT = "%s-%d";
    private final String SEPARATOR = "-";

    /**
     * SortedMap containing all users sorted by id asc.
     * The key is the user id
     * The value is the user object
     */
    private final SortedMap<String, User> users;

    /**
     * Map containing all properties.
     * The key is the property ID
     * The value is the property object
     */
    private final Map<String, Property> properties;

    /**
     * Map containing a Map of properties from a location with a certain capacity.
     * The key is the location
     * The value is a Map which has the capacity of certain properties as key and a list of
     * properties as value
     */
    private final Map<String, Map<Integer, List<Property>>> propertiesByLocationByCapacity;

    /**
     * Map containing a list of properties from a location
     * The key is the location
     * The value is a list of properties in that location
     */
    private final Map<String, List<Property>> propertiesByLocation;

    /**
     * Globe trotter is the guest that has visited more distinct locations
     * Is updated when a guest pays for a booking or a new booking is added
     */
    private Guest globeTrotter;

    /**
     * Constructor method which initializes the data structures
     */
    public DatabaseClass() {
        users = new TreeMap<>();
        properties = new HashMap<>();
        propertiesByLocation = new HashMap<>();
        propertiesByLocationByCapacity = new HashMap<>();
    }

    public Iterator<User> iteratorUsers() {
        return users.values().iterator();
    }

    @Override
    public boolean hasUsers() {
        return users.size() > 0;
    }

    public Iterator<Property> iteratorPropertiesByHost(String userID) {
        User user = getUser(userID);
        assert user != null;
        assert user instanceof Host;
        return ((Host) user).propertyIt();
    }

    public boolean hostHasProperties(String userID) throws UserDoesNotExistException, InvalidUserTypeException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.name().toLowerCase());
        return ((Host) user).hasProperties();
    }

    public void addGuest(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        Guest g = new GuestClass(identifier, name, nationality, email);
        users.put(identifier, g);
    }

    public void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        users.put(identifier, new HostClass(identifier, name, nationality, email));
    }

    public void addEntirePlace(String propertyID, String userID, String location, int capacity, double price, int numberOfRooms, String placeType) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = validateHostAndProperty(userID, propertyID);
        EntirePlace p = new EntirePlaceClass(propertyID, host, location, capacity, price, numberOfRooms, PlaceType.valueOf(placeType.toUpperCase()));
        addProperty(p, host, location, capacity);
    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, double price, int amenities) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = validateHostAndProperty(userID, propertyID);
        PrivateRoom p = new PrivateRoomClass(propertyID, host, location, capacity, price, amenities);
        addProperty(p, host, location, capacity);
    }

    /**
     * Registers a property in the database
     *
     * @param p        property to be registered
     * @param host     host of the property
     * @param location location of the property
     * @param capacity capacity of the property
     */
    private void addProperty(Property p, Host host, String location, int capacity) {
        properties.put(p.getIdentifier(), p);
        host.addProperty(p);
        if (!propertiesByLocation.containsKey(location)) {
            propertiesByLocation.put(location, new ArrayList<>());
            propertiesByLocationByCapacity.put(location, new HashMap<>(MAX_NUM_GUESTS + 1));
        }
        if (!propertiesByLocationByCapacity.get(location).containsKey(capacity))
            propertiesByLocationByCapacity.get(location).put(capacity, new ArrayList<>());

        propertiesByLocation.get(location).add(p);
        propertiesByLocationByCapacity.get(location).get(capacity).add(p);
    }

    /**
     * Return an host object with the given userID
     *
     * @param userID     ID of an user
     * @param propertyID ID of a property
     * @return an host object with the given userID
     * @throws UserDoesNotExistException     if no user was found
     * @throws InvalidUserTypeException      if the userID does not belong to an Host user
     * @throws PropertyAlreadyExistException if a property with the propertyID already exist
     */
    private Host validateHostAndProperty(String userID, String propertyID) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.name().toLowerCase());
        if (properties.containsKey(propertyID)) throw new PropertyAlreadyExistException(propertyID);
        return (Host) user;
    }

    public void addAmenity(String propertyID, String amenity) {
        PrivateRoom privateRoom = (PrivateRoom) getProperty(propertyID);
        privateRoom.addAmenity(amenity);
    }

    public Iterator<Booking> confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException, CannotExecuteActionInBookingException {
        Booking booking = validateHostAndBooking(bookingID, userID);
        Guest guest = booking.getGuest();
        Property property = properties.get(getPropertyIDFromBookingID(bookingID));

        guest.addConfirmedBooking(booking);
        property.addConfirmedBooking(booking);
        return property.confirmBooking(booking);
    }

    /**
     * Returns a booking object with the given bookingID
     *
     * @param bookingID id of a booking
     * @param userID    id of an user
     * @return a booking object with the given bookingID
     * @throws UserDoesNotExistException          if no user was found
     * @throws InvalidUserTypeException           if the userID does not belong to an Host
     * @throws BookingDoesNotExistException       if the bookingID is invalid
     * @throws InvalidUserTypeForBookingException if the host is not the owner of the property of
     *                                            the booking
     */
    private Booking validateHostAndBooking(String bookingID, String userID) throws UserDoesNotExistException, InvalidUserTypeException, BookingDoesNotExistException, InvalidUserTypeForBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.name().toLowerCase());

        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);

        if (!(booking.getHost().equals(user)))
            throw new InvalidUserTypeForBookingException(userID, UserType.HOST.name().toLowerCase(), bookingID);

        return booking;
    }

    public Booking addBooking(String userID, String propertyID, LocalDate arrival, LocalDate departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException, NumGuestsExceedsCapacityException, InvalidBookingDatesException, PropertyDoesNotExistException {
        User user = getUser(userID);

        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(userID, UserType.GUEST.name().toLowerCase());

        Guest guest = (Guest) user;
        Property property = getProperty(propertyID);

        if (property == null) throw new PropertyDoesNotExistException(propertyID);
        int guestsCapacity = property.getGuestsCapacity();
        if (guestsCapacity < numGuests)
            throw new NumGuestsExceedsCapacityException(propertyID, guestsCapacity);

        validateBookingDate(guest, property, arrival, departure);

        Booking b = new BookingClass(
                String.format(BOOKING_ID_FORMAT, propertyID, property.getBookingCount() + 1),
                guest,
                property,
                numGuests,
                arrival,
                departure,
                false);

        property.addBooking(b);
        guest.addBooking(b);
        updateGlobeTrotter(guest);
        return b;
    }

    /**
     * Validates if a new booking arrival date is valid.
     * A booking (arrival) date is considered valid if all the guest and property paid bookings
     * (past bookings) have departure dates before the arrival date of the booking being made.
     *
     * @param guest    guest which is trying to book the property
     * @param property property which is been booked
     * @param arrival  pretended booking arrival date
     * @throws InvalidBookingDatesException if the date is not valid
     */
    private void validateBookingDate(Guest guest, Property property, LocalDate arrival, LocalDate departure) throws InvalidBookingDatesException {
        if (guest.isDateInvalid(arrival, departure)) throw new InvalidBookingDatesException();
        if (property.isDateInvalid(arrival, departure)) throw new InvalidBookingDatesException();
    }

    public Iterator<Booking> iteratorRejections(String userID) {
        User user = getUser(userID);
        assert user != null;
        assert user instanceof Host;
        return ((Host) user).iteratorRejectedBookings();
    }

    public boolean hostHasRejectedBookings(String userID) throws UserDoesNotExistException, InvalidUserTypeException, UserHasNoBookingsException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.name().toLowerCase());

        Host host = (Host) user;

        if (host.getBookingsTotal() == 0) throw new UserHasNoBookingsException(userID);

        return host.getRejectedBookings() > 0;
    }

    public Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException, CannotExecuteActionInBookingException {
        Booking b = validateHostAndBooking(bookingID, userID);
        BookingState bState = b.getState();
        if (bState != BookingState.REQUESTED)
            throw new CannotExecuteActionInBookingException(Command.REJECT.name().toLowerCase(), bookingID, bState.name().toLowerCase());
        b.reject();
        return b;
    }

    public Iterator<Booking> pay(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, UserNotAllowedToPayBookingException, CannotExecuteActionInBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(userID, UserType.GUEST.name().toLowerCase());
        Guest guest = (Guest) user;
        if (!(booking.getGuest().equals(guest)))
            throw new UserNotAllowedToPayBookingException(userID);

        String propertyID = getPropertyIDFromBookingID(bookingID);
        Property property = getProperty(propertyID);

        Iterator<Booking> it = new IteratorOfTwoIterators(property.pay(booking), guest.pay(booking));

        updateGlobeTrotter(guest);

        return it;
    }

    public void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException, CannotExecuteActionInBookingException, BookingAlreadyReviewedException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(userID, UserType.GUEST.name().toLowerCase());
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        Guest guest = (Guest) user;
        if (!guest.hasBooking(booking))
            throw new InvalidUserTypeForBookingException(userID, UserType.GUEST.name().toLowerCase(), bookingID);
        if (!booking.isPaid())
            throw new CannotExecuteActionInBookingException(Command.REVIEW.name().toLowerCase(), bookingID, booking.getState().name().toLowerCase());
        if (booking.getState() == BookingState.REVIEWED)
            throw new BookingAlreadyReviewedException(bookingID);

        booking.review(review, classification);
    }

    public Guest getGuest(String guestID) {
        User user = getUser(guestID);
        assert user != null;
        assert user instanceof Guest;
        return (Guest) user;
    }

    public boolean guestHasBookings(String guestID) throws UserDoesNotExistException, InvalidUserTypeException {
        User user = getUser(guestID);
        if (user == null) throw new UserDoesNotExistException(guestID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(guestID, UserType.GUEST.name().toLowerCase());
        Guest guest = (Guest) user;
        return guest.getBookingsCount() > 0;
    }

    public Iterator<Booking> iteratorStaysAtProperty(String propertyID) {
        Property p = getProperty(propertyID);
        assert p != null;
        return p.iteratorPaidBookings();
    }

    public boolean propertyHasStays(String propertyID) throws PropertyDoesNotExistException {
        Property p = getProperty(propertyID);
        if (p == null) throw new PropertyDoesNotExistException(propertyID);
        return p.hasStays();
    }

    public Iterator<Property> iteratorPropertiesByCapacity(String location, int capacity) {
        assert capacity <= MAX_NUM_GUESTS;
        assert propertiesByLocationByCapacity.containsKey(location);

        List<Property> temp = new ArrayList<>();
        for (int i = capacity; i <= MAX_NUM_GUESTS; i++)
            if (propertiesByLocationByCapacity.get(location).containsKey(i))
                temp.addAll(propertiesByLocationByCapacity.get(location).get(i));

        temp.sort(new ComparatorSearch());
        return temp.iterator();
    }

    public Iterator<Property> iteratorPropertiesByAverage(String location) {
        assert propertiesByLocation.containsKey(location);
        List<Property> temp = new ArrayList<>(propertiesByLocation.get(location));
        temp.sort(new ComparatorBest());
        return temp.iterator();
    }

    public boolean hasProperty(String location, int capacity) {
        if (capacity > MAX_NUM_GUESTS) return false;
        if (!propertiesByLocation.containsKey(location)) return false;
        return !propertiesByLocation.get(location).isEmpty();
    }

    public boolean hasProperty(String location) {
        return hasProperty(location, 0);
    }

    public Guest getGlobeTrotter() throws NoGlobeTrotterException {
        if (globeTrotter == null) throw new NoGlobeTrotterException();
        return globeTrotter;
    }

    /**
     * Verifies if a guest which suffered changes should be the new Globe Trotter.
     * The globe trotter is the guest that has visited more locations In case of a draw, is
     * the guest with higher number of bookings. If a draw occurs again is the guest that comes
     * last in alphabetical order.
     *
     * @param guest guest which had modifications (payed for a booking or created a new booking)
     */
    private void updateGlobeTrotter(Guest guest) {
        if (globeTrotter == null && guest.getVisitedLocations() > 0) {
            globeTrotter = guest;
            return;
        }
        if (globeTrotter != null) {
            if (guest.getVisitedLocations() > globeTrotter.getVisitedLocations())
                globeTrotter = guest;
            else if (guest.getVisitedLocations() == globeTrotter.getVisitedLocations()) {
                if (guest.getBookingsCount() > globeTrotter.getBookingsCount())
                    globeTrotter = guest;
                else if (guest.getBookingsCount() == globeTrotter.getBookingsCount())
                    if (guest.getIdentifier().compareTo(globeTrotter.getIdentifier()) > 0)
                        globeTrotter = guest;
            }
        }
    }

    /**
     * Returns an user object if the user exists or <code>null</code> if the userID does not exist
     *
     * @param userID ID of an user
     * @return an user object if the userID exists or <code>null</code> if the userID does not
     * exist
     */
    private User getUser(String userID) {
        return users.get(userID);
    }

    /**
     * Returns a property object if the propertyID exists or <code>null</code> if the propertyID
     * does not exist.
     *
     * @param propertyID ID of a property
     * @return a property object if the propertyID exists or <code>null</code> if the propertyID
     * does not exist.
     */
    private Property getProperty(String propertyID) {
        return properties.get(propertyID);
    }

    /**
     * Returns a String with the ID of a property in the format: property-ID
     *
     * @param bookingID ID of a booking in the format: property-ID-bookingID
     * @return a String with the ID of a property in the format: property-ID
     */
    private String getPropertyIDFromBookingID(String bookingID) {
        int i = bookingID.lastIndexOf(SEPARATOR);
        return bookingID.substring(0, i);
    }

    /**
     * Returns a booking object if the bookingID exists or <code>null</code> if the bookingID
     * does not exist.
     *
     * @param bookingID ID of a booking
     * @return a booking object if the bookingID exists or <code>null</code> if the booking
     * with the given bookingID does not exist.
     */
    private Booking getBooking(String bookingID) {
        String propertyID = getPropertyIDFromBookingID(bookingID);
        Property p = properties.get(propertyID);
        Guest tempUser = new GuestClass(null, null, null, null);
        Booking temp = new BookingClass(bookingID, tempUser, p, 0, null, null, true);

        return p.getBooking(temp);
    }
}
