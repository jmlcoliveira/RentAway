package database;

import booking.Booking;
import exceptions.*;
import property.Property;
import users.GuestClass;
import users.HostClass;
import users.User;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class DatabaseClass implements Database{

    SortedMap<String, User> users;

    public DatabaseClass(){
        users = new TreeMap<>();
    }

    public Iterator<User> iteratorUsers() throws NoUsersRegisteredException {
        return users.values().iterator();
    }

    public Iterator<Property> propertyIt(String id) throws UserDoesNotExistException, InvalidUserTypeException, NoPropertiesRegisteredException {
        return null;
    }

    public void addGuest(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        users.put(identifier, new GuestClass(identifier, name, nationality, email));
    }

    public void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException {
        users.put(identifier, new HostClass(identifier, name, nationality, email));
    }

    public void addEntirePlace(String propertyID, String userID, String location, int capacity, int price, int numberOfRooms, String placeType) throws UserDoesNotExistException, PropertyAlreadyExistException {

    }

    public void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException, PropertyAlreadyExistException {

    }

    public void addAmenity(String propertyID, String amenity) {

    }

    public void confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException, UserNotAllowedToConfirmBookingException, BookingNotInRequestedStateException {

    }

    public Booking addBooking(String userID, String propertyID, String arrival, String departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException, NumGuestsExceedsCapacityException, InvalidBookingDatesException {
        return null;
    }
}
