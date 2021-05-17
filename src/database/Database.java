package database;

import exceptions.*;
import property.Property;
import users.User;
import booking.Booking;

import java.util.Iterator;

public interface Database {

    Iterator<User> iteratorUsers() throws NoUsersRegisteredException;

    Iterator<Property> propertyIt(String id) throws UserDoesNotExistException,
            InvalidUserTypeException,
            NoPropertiesRegisteredException;

    void addGuest(String identifier, String name, String nationality, String email) throws UserAlreadyExistException;

    void addHost(String identifier, String name, String nationality, String email) throws UserAlreadyExistException;

    void addEntirePlace(String propertyID, String userID, String location, int capacity, int price, int numberOfRooms, String placeType) throws UserDoesNotExistException,
            PropertyAlreadyExistException;

    void addPrivateRoom(String propertyID, String userID, String location, int capacity, int price, int amenities) throws UserDoesNotExistException,
            PropertyAlreadyExistException;

    void addAmenity(String propertyID, String amenity);

    void confirmBooking(String bookingID, String userID) throws BookingDoesNotExistException, UserDoesNotExistException,
            UserNotAllowedToConfirmBookingException, BookingNotInRequestedStateException;

    Booking addBooking(String userID, String propertyID, String arrival,
                    String departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException,
            NumGuestsExceedsCapacityException, InvalidBookingDatesException;

}
