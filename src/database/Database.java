package database;

import booking.Booking;
import exceptions.*;
import property.Property;
import users.*;

import java.util.Iterator;

public interface Database {

    Iterator<User> iteratorUsers() throws NoUsersRegisteredException;

    Iterator<Property> iteratorProperties(String id) throws UserDoesNotExistException,
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
            UserNotAllowedToConfirmBookingException, CannotConfirmBookingException;

    booking.Booking addBooking(String userID, String propertyID, String arrival,
                               String departure, int numGuests) throws UserDoesNotExistException, InvalidUserTypeException,
            NumGuestsExceedsCapacityException, InvalidBookingDatesException;

    Iterator<booking.Booking> iteratorRejections(String userID) throws UserDoesNotExistException,
            InvalidUserTypeException, UserHasNoBookingsException,
            HostHasNotRejectedBookingsException;

    Booking rejectBooking(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException,UserNotAllowedToConfirmBookingException, CannotConfirmBookingException;

    Booking pay(String bookingID, String userID) throws BookingDoesNotExistException,
            UserDoesNotExistException, UserNotGuestOfBookingException,
            CannotConfirmBookingException;

    Iterator<booking.Booking> iteratorBookingByDates(String bookingID, String userID);

    void addReview(String bookingID, String userID, String review, String classification) throws BookingDoesNotExistException, UserDoesNotExistException,
            UserNotAllowedToReview, CannotReviewBookingException,
            BookingAlreadyReviewedException;

    Host getHost(String hostID) throws HostHasNoPropertiesException, UserDoesNotExistException,
            InvalidUserTypeException;

    Guest getGuest(String guestID) throws GuestHasNoBookingsException, UserDoesNotExistException,
            InvalidUserTypeException;

    Iterator<Property> iteratorPropertiesByHost(String hostID);

    Iterator<Booking> iteratorStaysAtProperty(String propertyID) throws PropertyHasNoStaysException, PropertyDoesNotExistException;

    Iterator<Property> iteratorPropertiesByLocation(String location, int numGuests) throws NoPropertyInLocationException;
}
