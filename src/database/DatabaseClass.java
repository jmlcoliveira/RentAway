package database;

import booking.*;
import exceptions.*;
import property.*;
import users.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class DatabaseClass implements Database {

    private final SortedMap<String, User> users;
    private final Map<String, Property> properties;
    private final Map<String, List<Booking>> rejectedBookingsByHostID;

    public DatabaseClass() {
        users = new TreeMap<>();
        properties = new HashMap<>();
        rejectedBookingsByHostID = new HashMap<>();
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
        users.put(identifier, new GuestClass(identifier, name, nationality, email));
    }

    public void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        users.put(identifier, new HostClass(identifier, name, nationality, email));
    }

    public void addEntirePlace(String userID, String propertyID, String location, int capacity,
                               int price, int numberOfRooms, String placeType) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        properties.put(propertyID, new EntirePlaceClass(propertyID, host, location, capacity,
                price, numberOfRooms, PlaceType.valueOf(placeType.toUpperCase())));
    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException, InvalidUserTypeException, PropertyAlreadyExistException {
        Host host = addToHost(userID, propertyID);
        properties.put(propertyID, new PrivateRoomClass(propertyID, host, location, capacity,
                price, amenities));
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

    public void confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToConfirmBookingException, CannotConfirmBookingException {
        String propertyID = getPropertyID(bookingID);
        Booking temp = new BookingClass(bookingID, null, null, 0, null, null);
        List<Booking> bookings = getProperty(propertyID).getBookings();
        if (!bookings.contains(temp)) throw new BookingDoesNotExistException(bookingID);

        User user = getUser(userID);
        if (user == null) throw new UserDoesNotExistException(userID);

        if (!(user instanceof Host)) throw new UserNotAllowedToConfirmBookingException(userID);

        bookings.get(bookings.indexOf(temp)).confirm();
    }

    private String getPropertyID(String bookingID) {
        int i = bookingID.lastIndexOf('-');
        return bookingID.substring(0, i);
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
            } catch (CannotConfirmBookingException e) {
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
        if(user == null) throw new UserDoesNotExistException(userID);

        if(!(user instanceof Host)) throw new InvalidUserTypeException(userID,
                UserType.HOST.getType());

        Host host = (Host) user;
        if(host.getBookingsTotal() == 0) throw new UserHasNoBookingsException(userID);

        if(host.getRejectedBookings() == 0) throw new HostHasNotRejectedBookingsException(userID);

        return rejectedBookingsByHostID.get(userID).iterator();
    }

    public Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, InvalidUserTypeException, CannotConfirmBookingException {
        User user = getUser(userID);
        if(user == null) throw new UserDoesNotExistException(userID);
        if(!(user instanceof Host)) throw new InvalidUserTypeException(userID, UserType.HOST.getType());

    }

    public Booking pay(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotGuestOfBookingException, CannotConfirmBookingException {
        return null;
    }

    public Iterator<Booking> iteratorBookingByDates(String bookingID, String userID) {
        return null;
    }

    public void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToReview, CannotReviewBookingException, BookingAlreadyReviewedException {

    }

    public Host getHost(String hostID) throws HostHasNoPropertiesException, UserDoesNotExistException, InvalidUserTypeException {
        return null;
    }

    public Guest getGuest(String guestID) throws GuestHasNoBookingsException, UserDoesNotExistException, InvalidUserTypeException {
        return null;
    }

    public Iterator<Booking> iteratorStaysAtProperty(String propertyID) throws PropertyHasNoStaysException, PropertyDoesNotExistException {
        return null;
    }

    public Iterator<Property> iteratorPropertiesByGuest(String location, int numGuests) throws NoPropertyInLocationException {
        return null;
    }

    public Iterator<Property> iteratorPropertiesByAverage(String location) throws NoPropertyInLocationException {
        return null;
    }

    public Guest getGlobalTrotter() throws NoGlobalTrotterException {
        return null;
    }

    private User getUser(String identifier) {
        return users.get(identifier);
    }

    private Property getProperty(String identifier) {
        return properties.get(identifier);
    }
}
