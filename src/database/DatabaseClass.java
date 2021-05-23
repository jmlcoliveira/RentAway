package database;

import booking.*;
import commands.Command;
import exceptions.*;
import property.*;
import users.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class DatabaseClass implements Database {

    private final SortedMap<String, User> users;
    private final Map<String, Guest> guests;
    private final Map<String, Property> properties;
    private final Map<String, List<Property>> propertiesByLocation;

    public DatabaseClass() {
        users = new TreeMap<>();
        guests = new HashMap<>();
        properties = new HashMap<>();
        propertiesByLocation = new HashMap<>();
    }

    public Iterator<User> iteratorUsers() throws NoUsersRegisteredException {
        if (users.isEmpty()) throw new NoUsersRegisteredException();
        return users.values().iterator();
    }

    public Iterator<Property> iteratorPropertiesByHost(String identifier) throws UserDoesNotExistException, InvalidUserTypeException, NoPropertiesRegisteredException {
        User user = getUser(identifier);
        if (user == null) throw new UserDoesNotExistException(identifier);
        if (!(user instanceof Host)) throw new InvalidUserTypeException(identifier,
                UserType.GUEST.getType());
        return ((Host) user).propertyIt();
    }

    public void addGuest(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        Guest g = new GuestClass(identifier, name, nationality, email);
        users.put(identifier, g);
        guests.put(identifier, g);
    }

    public void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        users.put(identifier, new HostClass(identifier, name, nationality, email));
    }

    public void addEntirePlace(String userID, String propertyID, String location, int capacity,
                               int price, int numberOfRooms, String placeType) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        EntirePlace p = new EntirePlaceClass(propertyID, host, location, capacity,
                price, numberOfRooms, PlaceType.valueOf(placeType.toUpperCase()));
        properties.put(propertyID, p);
        if (!propertiesByLocation.containsKey(location))
            propertiesByLocation.put(location, new LinkedList<>());
        propertiesByLocation.get(location).add(p);
    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        PrivateRoom p = new PrivateRoomClass(propertyID, host, location, capacity,
                price, amenities);
        properties.put(propertyID, p);
        if (!propertiesByLocation.containsKey(location))
            propertiesByLocation.put(location, new LinkedList<>());
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

    public Iterator<Booking> confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToConfirmBookingException, CannotExecuteActionInBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);

        if (!(user instanceof Host)) throw new UserNotAllowedToConfirmBookingException(userID);
        Property property = properties.get(getPropertyID(bookingID));
        return property.confirmBooking(booking);
    }

    public Booking addBooking(String userID, String propertyID, LocalDate arrival, LocalDate departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException, NumGuestsExceedsCapacityException, InvalidBookingDatesException, PropertyIdDoesNotExistException {
        User user = getUser(userID);

        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest)) throw new InvalidUserTypeException(userID,
                UserType.GUEST.getType());

        Guest guest = (Guest) user;
        Property property = getProperty(propertyID);

        if (property == null) throw new PropertyIdDoesNotExistException(propertyID);
        int guestsCapacity = property.getGuestsCapacity();
        if (guestsCapacity < numGuests) throw new NumGuestsExceedsCapacityException(propertyID,
                guestsCapacity);

        validateBookingDate(guest, property, arrival);
        Booking b = new BookingClass(
                property.getBookingCount() + 1 + "",
                guest,
                property,
                numGuests, arrival,
                departure);
        if (property.getType() == PropertyType.ENTIRE_PLACE && Duration.between(arrival.atStartOfDay(),
                departure.atStartOfDay()).toDays() > 7) {
            try {
                b.confirm();
            } catch (CannotExecuteActionInBookingException e) {
                //nothing will happen
            }
        }
        return b;
    }

    private void validateBookingDate(Guest guest, Property property, LocalDate arrival) throws InvalidBookingDatesException {
        LocalDate guestLastDepartureDate = guest.getLastDepartureDate();
        LocalDate propertyLastPaidDepartureDate = property.getPropertyLastPaidDepartureDate();
        if (arrival.isBefore(guestLastDepartureDate) && arrival.isBefore(propertyLastPaidDepartureDate))
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

    public Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, CannotExecuteActionInBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Host))
            throw new InvalidUserTypeException(userID, UserType.HOST.getType());
        Booking b = getBooking(bookingID);
        if (b == null) throw new BookingDoesNotExistException(bookingID);
        BookingState bState = b.getState();
        if (bState != BookingState.REQUESTED)
            throw new CannotExecuteActionInBookingException(Command.REJECT.getCommand(), bookingID, bState.getStateValue());
        b.reject();
        return b;
    }

    public Iterator<Booking> pay(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException, UserNotGuestOfBookingException, CannotExecuteActionInBookingException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        if (!(user instanceof Guest)) throw new UserNotGuestOfBookingException(userID);
        Guest guest = (Guest) user;
        if (!(guest.hasBooking(booking))) throw new UserNotGuestOfBookingException(userID);

        String propertyID = getPropertyID(bookingID);
        Property property = getProperty(propertyID);

        Iterator<Booking> propertyBookingIt = property.pay(booking);
        Iterator<Booking> guestBookingIt = guest.pay(booking);

        List<Booking> bookings = new LinkedList<>();

        while (propertyBookingIt.hasNext())
            bookings.add(propertyBookingIt.next());
        while (guestBookingIt.hasNext())
            bookings.add(guestBookingIt.next());

        return bookings.iterator();
    }


    public void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, UserNotAllowedToReviewException, CannotExecuteActionInBookingException, BookingAlreadyReviewedException {
        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);
        if (!(user instanceof Guest)) throw new InvalidUserTypeException(userID,
                UserType.GUEST.getType());
        Booking booking = getBooking(bookingID);
        if (booking == null) throw new BookingDoesNotExistException(bookingID);
        Guest guest = (Guest) user;
        if (!guest.hasBooking(booking)) throw new UserNotAllowedToReviewException(userID,
                bookingID);
        if (!(booking.getState().equals(BookingState.PAID)))
            throw new CannotExecuteActionInBookingException(Command.REVIEW.getCommand(), bookingID, booking.getState().getStateValue());
        booking.review(review, classification);
    }

    public Guest getGuest(String guestID) throws GuestHasNoBookingsException, UserDoesNotExistException, InvalidUserTypeException {
        User user = getUser(guestID);
        if (user == null) throw new UserDoesNotExistException(guestID);
        if (!(user instanceof Guest))
            throw new InvalidUserTypeException(guestID, UserType.GUEST.getType());
        Guest guest = (Guest) user;
        if (guest.getBookingsTotal() == 0) throw new GuestHasNoBookingsException(guestID);
        return guest;
    }

    public Iterator<Booking> iteratorStaysAtProperty(String propertyID) throws PropertyHasNoStaysException, PropertyDoesNotExistException {
        Property p = getProperty(propertyID);
        if (p == null) throw new PropertyDoesNotExistException(propertyID);
        if (p.getPaidBookingCount() == 0) throw new PropertyHasNoStaysException(propertyID);
        return null;
    }

    public Iterator<Property> iteratorPropertiesByGuest(String location, int numGuests) throws NoPropertyInLocationException {
        Iterator<Property> it = propertiesByLocation.get(location).iterator();
        if (!it.hasNext()) throw new NoPropertyInLocationException(location);

        List<Property> properties = new LinkedList<>();
        while (it.hasNext()) {
            Property next = it.next();
            if (next.getGuestsCapacity() >= numGuests)
                properties.add(next);
        }

        Collections.sort(properties, new ComparatorSearch());
        return properties.iterator();
    }

    public Iterator<Property> iteratorPropertiesByAverage(String location) throws NoPropertyInLocationException {
        List<Property> properties = propertiesByLocation.get(location);
        if (properties.isEmpty()) throw new NoPropertyInLocationException(location);

        Collections.sort(properties, new ComparatorBest());
        return properties.iterator();
    }

    public Guest getGlobalTrotter() throws NoGlobalTrotterException {
        if (guests.size() == 0) throw new NoGlobalTrotterException();
        Iterator<Guest> it = guests.values().iterator();

        Guest globalTrotter = null;
        while (it.hasNext()) {
            Guest next = it.next();
            if (globalTrotter == null && next.getVisitedLocations() > 0) {
                globalTrotter = next;
                continue;
            }
            if (globalTrotter != null) {
                if (next.getVisitedLocations() > globalTrotter.getVisitedLocations())
                    globalTrotter = next;
                else if (next.getVisitedLocations() == globalTrotter.getVisitedLocations()) {
                    if (next.getBookingsTotal() > globalTrotter.getBookingsTotal())
                        globalTrotter = next;
                    else if (next.getBookingsTotal() == globalTrotter.getBookingsTotal())
                        if (next.getIdentifier().compareTo(globalTrotter.getIdentifier()) > 0)
                            globalTrotter = next;
                }
            }
        }
        if (globalTrotter == null) throw new NoGlobalTrotterException();
        return globalTrotter;
    }

    private User getUser(String identifier) {
        return users.get(identifier);
    }

    private Property getProperty(String identifier) {
        return properties.get(identifier);
    }

    private String getPropertyID(String bookingID) {
        int i = bookingID.lastIndexOf('-');
        return bookingID.substring(0, i);
    }

    private Booking getBooking(String bookingID) {
        String propertyID = getPropertyID(bookingID);
        Property p = properties.get(propertyID);
        Booking temp = new BookingClass(bookingID, null, p, 0, null, null);
        List<Booking> bookings = p.getBookings();
        return bookings.get(bookings.indexOf(temp));
    }
}
