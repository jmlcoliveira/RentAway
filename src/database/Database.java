package database;

import booking.Booking;
import exceptions.*;
import property.Property;
import users.*;

import java.time.LocalDate;
import java.util.Iterator;

public interface Database {

    Iterator<User> iteratorUsers() throws NoUsersRegisteredException;

    Iterator<Property> iteratorPropertiesByHost(String id)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            NoPropertiesRegisteredException;

    void addGuest(String identifier, String name, String nationality, String email)
            throws UserAlreadyExistException;

    void addHost(String identifier, String name, String nationality, String email)
            throws UserAlreadyExistException;

    void addEntirePlace(String propertyID, String userID, String location, int capacity,
                        int price, int numberOfRooms, String placeType)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    void addPrivateRoom(String propertyID, String userID, String location, int capacity,
                        int price, int amenities)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    void addAmenity(String propertyID, String amenity);

    Iterator<Booking> confirmBooking(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            UserNotAllowedToConfirmBookingException,
            CannotExecuteActionInBookingException;

    booking.Booking addBooking(String userID, String propertyID, LocalDate arrival,
                               LocalDate departure, int numGuests)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            NumGuestsExceedsCapacityException,
            InvalidBookingDatesException,
            PropertyIdDoesNotExistException;

    Iterator<booking.Booking> iteratorRejections(String userID)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            UserHasNoBookingsException,
            HostHasNotRejectedBookingsException;

    Booking rejectBooking(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            CannotExecuteActionInBookingException;

    Iterator<Booking> pay(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            UserNotGuestOfBookingException,
            CannotExecuteActionInBookingException;


    void addReview(String bookingID, String userID, String review, String classification)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            UserNotAllowedToReviewException,
            CannotExecuteActionInBookingException,
            BookingAlreadyReviewedException;

    Guest getGuest(String guestID)
            throws GuestHasNoBookingsException,
            UserDoesNotExistException,
            InvalidUserTypeException;

    Iterator<Booking> iteratorStaysAtProperty(String propertyID)
            throws PropertyHasNoStaysException,
            PropertyDoesNotExistException;

    Iterator<Property> iteratorPropertiesByGuest(String location, int numGuests)
            throws NoPropertyInLocationException;

    Iterator<Property> iteratorPropertiesByAverage(String location)
            throws NoPropertyInLocationException;

    Guest getGlobalTrotter()
            throws NoGlobalTrotterException;
}
