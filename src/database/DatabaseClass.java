package database;

import booking.*;
import commands.Command;
import exceptions.*;
import exceptions.booking.*;
import exceptions.property.*;
import exceptions.user.InvalidUserTypeException;
import exceptions.user.NoUsersRegisteredException;
import exceptions.user.UserAlreadyExistException;
import exceptions.user.UserDoesNotExistException;
import property.*;
import users.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class DatabaseClass implements Database {

    private final SortedMap<String, User> users;
    private final Map<String, Property> properties;
    private final Map<String, SortedSet<Property>> propertiesByLocation;
    private Guest globeTrotter;

    public DatabaseClass() {
        users = new TreeMap<>();
        properties = new HashMap<>();
        propertiesByLocation = new HashMap<>();
        globeTrotter = null;
    }

    public Iterator<User> iteratorUsers() throws NoUsersRegisteredException {
        if (users.isEmpty()) throw new NoUsersRegisteredException();
        return users.values().iterator();
    }

    public Iterator<Property> iteratorPropertiesByHost(String identifier) throws UserDoesNotExistException, InvalidUserTypeException, NoPropertiesRegisteredException {
        User user = getUser(identifier);
        if (user == null) throw new UserDoesNotExistException(identifier);
        if (!(user instanceof Host)) throw new InvalidUserTypeException(identifier,
                UserType.HOST.getType());
        return ((Host) user).propertyIt();
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

    public void addEntirePlace(String propertyID, String userID, String location, int capacity,
                               int price, int numberOfRooms, String placeType) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        EntirePlace p = new EntirePlaceClass(propertyID, host, location, capacity,
                price, numberOfRooms, PlaceType.valueOf(placeType.toUpperCase()));
        properties.put(propertyID, p);
        host.addProperty(p);
        if (!propertiesByLocation.containsKey(location))
            propertiesByLocation.put(location, new TreeSet<>(new ComparatorCapacityDesc()));
        propertiesByLocation.get(location).add(p);
    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        PrivateRoom p = new PrivateRoomClass(propertyID, host, location, capacity,
                price, amenities);
        properties.put(propertyID, p);
        host.addProperty(p);
        if (!propertiesByLocation.containsKey(location))
            propertiesByLocation.put(location, new TreeSet<>(new ComparatorCapacityDesc()));
        propertiesByLocation.get(location).add(p);
    }

    private Host addToHost(String userID, String propertyID) throws UserDoesNotExistException,
            InvalidUserTypeException, PropertyAlreadyExistException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host)) throw new InvalidUserTypeException(userID,
                UserType.HOST.getType());
        if (properties.containsKey(propertyID)) throw new PropertyAlreadyExistException(propertyID);
        return (Host) user;
    }

    public void addAmenity(String propertyID, String amenity) {
        PrivateRoom privateRoom = (PrivateRoom) getProperty(propertyID);
        privateRoom.addAmenity(amenity);
    }

    public Iterator<Booking> confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException {
        Booking booking = validateUserAndBooking(bookingID, userID);
        Property property = properties.get(getPropertyIDFromBookingID(bookingID));
        return property.confirmBooking(booking);
    }

    private Booking validateUserAndBooking(String bookingID, String userID) throws UserDoesNotExistException, InvalidUserTypeException, BookingDoesNotExistException, InvalidUserTypeForBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.getType());

        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);

        if (!(booking.getHost().equals(user)))
            throw new InvalidUserTypeForBookingException(userID, UserType.HOST.getType(),
                    bookingID);
        return booking;
    }

    public Booking addBooking(String userID, String propertyID, LocalDate arrival, LocalDate departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException, NumGuestsExceedsCapacityException, InvalidBookingDatesException, PropertyDoesNotExistException {
        User user = getUser(userID);

        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest)) throw new InvalidUserTypeException(userID,
                UserType.GUEST.getType());

        Guest guest = (Guest) user;
        Property property = getProperty(propertyID);

        if (property == null) throw new PropertyDoesNotExistException(propertyID);
        int guestsCapacity = property.getGuestsCapacity();
        if (guestsCapacity < numGuests) throw new NumGuestsExceedsCapacityException(propertyID,
                guestsCapacity);

        validateBookingDate(guest, property, arrival);
        Booking b = new BookingClass(
                String.format("%s-%d", propertyID, property.getBookingCount() + 1),
                guest,
                property,
                numGuests, arrival,
                departure);
        if (property.getType() == PropertyType.ENTIRE_PLACE && Duration.between(arrival.atStartOfDay(),
                departure.atStartOfDay()).toDays() > 7 && !property.bookingOverlaps(b)) {
            try {
                b.confirm();
            } catch (CannotExecuteActionInBookingException e) {
                //nothing will happen
            }
        }
        property.addBooking(b);
        property.getHost().addBooking(b);
        guest.addBooking(b);
        return b;
    }

    private void validateBookingDate(Guest guest, Property property, LocalDate arrival) throws InvalidBookingDatesException {
        LocalDate guestLastPaidDepartureDate = guest.getLastPaidDepartureDate();
        LocalDate propertyLastPaidDepartureDate = property.getPropertyLastPaidDepartureDate();
        if (guestLastPaidDepartureDate == null && propertyLastPaidDepartureDate == null)
            return;
        if (guestLastPaidDepartureDate == null) {
            if (arrival.isBefore(propertyLastPaidDepartureDate))
                throw new InvalidBookingDatesException();
            return;
        }
        if (propertyLastPaidDepartureDate == null) {
            if (arrival.isBefore(guestLastPaidDepartureDate))
                throw new InvalidBookingDatesException();
            return;
        }

        if (arrival.isBefore(guestLastPaidDepartureDate) && arrival.isBefore(propertyLastPaidDepartureDate))
            throw new InvalidBookingDatesException();
    }

    public Iterator<Booking> iteratorRejections(String userID) throws UserDoesNotExistException,
            InvalidUserTypeException, UserHasNoBookingsException, HostHasNotRejectedBookingsException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host)) throw new InvalidUserTypeException(userID,
                UserType.HOST.getType());

        Host host = (Host) user;
        if (host.getBookingsTotal() == 0) throw new UserHasNoBookingsException(userID);

        if (host.getRejectedBookings() == 0) throw new HostHasNotRejectedBookingsException(userID);

        return host.iteratorRejectedBookings();
    }

    public Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException {
        Booking b = validateUserAndBooking(bookingID, userID);
        BookingState bState = b.getState();
        if (bState != BookingState.REQUESTED)
            throw new CannotExecuteActionInBookingException(Command.REJECT.getCommand(), bookingID, bState.getStateValue());
        b.reject();
        return b;
    }

    public Iterator<Booking> pay(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException, InvalidUserTypeException, UserNotAllowedToPayBookingException,
            CannotExecuteActionInBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(userID, UserType.GUEST.getType());
        Guest guest = (Guest) user;
        if (!(booking.getGuest().equals(guest)))
            throw new UserNotAllowedToPayBookingException(userID);

        String propertyID = getPropertyIDFromBookingID(bookingID);
        Property property = getProperty(propertyID);

        Iterator<Booking> propertyBookingIt = property.pay(booking);
        Iterator<Booking> guestBookingIt = guest.pay(booking);

        updateGlobeTrotter(guest);

        List<Booking> bookings = new LinkedList<>();

        while (propertyBookingIt.hasNext())
            bookings.add(propertyBookingIt.next());
        while (guestBookingIt.hasNext())
            bookings.add(guestBookingIt.next());

        return bookings.iterator();
    }


    public void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException,
            UserDoesNotExistException, InvalidUserTypeException, InvalidUserTypeForBookingException, CannotExecuteActionInBookingException,
            BookingAlreadyReviewedException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest)) throw new InvalidUserTypeException(userID,
                UserType.GUEST.getType());
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        Guest guest = (Guest) user;
        if (!guest.hasBooking(booking))
            throw new InvalidUserTypeForBookingException(userID, UserType.GUEST.getType(),
                    bookingID);
        if (!booking.isPaid())
            throw new CannotExecuteActionInBookingException(Command.REVIEW.getCommand(), bookingID, booking.getState().getStateValue());
        if (booking.getState() == BookingState.REVIEWED)
            throw new BookingAlreadyReviewedException(bookingID);
        booking.review(review, classification);
    }

    public Guest getGuest(String guestID) throws GuestHasNoBookingsException, UserDoesNotExistException, InvalidUserTypeException {
        User user = getUser(guestID);
        if (user == null) throw new UserDoesNotExistException(guestID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(guestID, UserType.GUEST.getType());
        Guest guest = (Guest) user;
        if (guest.getBookingsCount() == 0) throw new GuestHasNoBookingsException(guestID);
        return guest;
    }

    public Iterator<Booking> iteratorStaysAtProperty(String propertyID) throws NoPropertyInLocationException, PropertyDoesNotExistException {
        Property p = getProperty(propertyID);
        if (p == null) throw new PropertyDoesNotExistException(propertyID);
        if (p.getPaidBookingCount() == 0) throw new NoPropertyInLocationException(propertyID);
        return p.iteratorPaidBookings();
    }

    public Iterator<Property> iteratorPropertiesByCapacity(String location, int capacity) throws NoPropertyInLocationException {
        if (!propertiesByLocation.containsKey(location))
            throw new NoPropertyInLocationException(location);
        Iterator<Property> it = propertiesByLocation.get(location).iterator();
        if (!it.hasNext()) throw new NoPropertyInLocationException(location);

        List<Property> properties = new ArrayList<>();
        while (it.hasNext()) {
            Property next = it.next();
            if (next.getGuestsCapacity() >= capacity)
                properties.add(next);
            else
                break;
        }

        if (properties.size() == 0) throw new NoPropertyInLocationException(location);

        Collections.sort(properties, new ComparatorSearch());
        return properties.iterator();
    }

    public Iterator<Property> iteratorPropertiesByAverage(String location) throws NoPropertyInLocationException {
        List<Property> properties = new ArrayList<>(propertiesByLocation.get(location));
        if (properties.isEmpty()) throw new NoPropertyInLocationException(location);

        Collections.sort(properties, new ComparatorBest());
        return properties.iterator();
    }

    public Guest getGlobeTrotter() throws NoGlobalTrotterException {
        if (globeTrotter == null) throw new NoGlobalTrotterException();
        return globeTrotter;
    }

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

    private User getUser(String identifier) {
        return users.get(identifier);
    }

    private Property getProperty(String identifier) {
        return properties.get(identifier);
    }

    private String getPropertyIDFromBookingID(String bookingID) {
        int i = bookingID.lastIndexOf('-');
        return bookingID.substring(0, i);
    }

    private Booking getBooking(String bookingID) {
        String propertyID = getPropertyIDFromBookingID(bookingID);
        Property p = properties.get(propertyID);
        Guest tempUser = new GuestClass(null, null, null, null);
        Booking temp = new BookingClass(bookingID, tempUser, p, 0,
                null,
                null);
        return p.getBooking(temp);
    }
}
