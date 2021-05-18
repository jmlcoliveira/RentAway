package database;

import booking.*;
import exceptions.*;
import property.*;
import users.*;

import java.util.*;

public class DatabaseClass implements Database {

    SortedMap<String, User> users;
    Map<String, Property> properties;

    public DatabaseClass() {
        users = new TreeMap<>();
        properties = new HashMap<>();
    }

    public Iterator<User> iteratorUsers() throws NoUsersRegisteredException {
        if (users.isEmpty()) throw new NoUsersRegisteredException();
        return users.values().iterator();
    }

    public Iterator<Property> iteratorProperties(String identifier) throws UserDoesNotExistException, InvalidUserTypeException, NoPropertiesRegisteredException {
        User user = getUser(identifier);
        if(user == null) throw new UserDoesNotExistException(identifier);
        if (user instanceof Guest) throw new InvalidUserTypeException(identifier, "guest");
        return null;
    }

    public void addGuest(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
            users.put(identifier, new GuestClass(identifier, name, nationality, email));
    }

    public void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        if (getUser(identifier) != null) throw new UserAlreadyExistException(identifier);
        users.put(identifier, new HostClass(identifier, name, nationality, email));
    }

    public void addEntirePlace(String propertyID, String userID, String location, int capacity, int price, int numberOfRooms, String placeType) throws UserDoesNotExistException, PropertyAlreadyExistException {

    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException, PropertyAlreadyExistException {

    }

    public void addAmenity(String propertyID, String amenity) {

    }

    public void confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToConfirmBookingException, CannotConfirmBookingException {

    }

    public Booking addBooking(String userID, String propertyID, String arrival, String departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException, NumGuestsExceedsCapacityException, InvalidBookingDatesException {
        return null;
    }

    public Iterator<Booking> iteratorRejections(String userID) throws UserDoesNotExistException, InvalidUserTypeException, UserHasNoBookingsException, HostHasNotRejectedBookingsException {
        return null;
    }

    public Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToConfirmBookingException, CannotConfirmBookingException {
        return null;
    }

    public Booking pay(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotGuestOfBookingException, CannotConfirmBookingException {
        return null;
    }

    public Iterator<Booking> iteratorBookingByDates(String bookingID, String userID) {
        return null;
    }

    public void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToReview, CannotReviewBookingException, BookingAlreadyReviewedException {

    }

    private User getUser(String identifier) {
        return users.get(identifier);
    }

    private Property getProperty(String identifier) { return properties.get(identifier); }
}
