package database;

import booking.Booking;
import exceptions.booking.*;
import exceptions.property.*;
import exceptions.user.*;
import property.Property;
import users.Guest;
import users.User;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Database {

    /**
     * Returns an Iterator with every user in the database
     *
     * @return Iterator with every registered user
     * @throws NoUsersRegisteredException if there are no users registered
     */
    Iterator<User> iteratorUsers() throws NoUsersRegisteredException;

    /**
     * Returns an Iterator with every property of a given host
     *
     * @param id the ID of the host
     * @return Iterator with the properties of the host
     * @throws UserDoesNotExistException        if no user was found
     * @throws InvalidUserTypeException         if the user with that userID is not a host
     * @throws NoPropertiesRegisteredException  if there are no properties registered
     */
    Iterator<Property> iteratorPropertiesByHost(String id)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            NoPropertiesRegisteredException;

    /**
     * Adds a guest to the database
     *
     * @param identifier    ID of the guest
     * @param name          name of the guest
     * @param nationality   nationality of the guest
     * @param email         email of the guest
     * @throws UserAlreadyExistException    if there is already a user with the same identifier
     */
    void addGuest(String identifier, String name, String nationality, String email)
            throws UserAlreadyExistException;

    /**
     * Adds a host to the database
     *
     * @param identifier    ID of the host
     * @param name          name of the host
     * @param nationality   nationality of the host
     * @param email         email of the host
     * @throws UserAlreadyExistException    if there is already a user with the same identifier
     */
    void addHost(String identifier, String name, String nationality, String email)
            throws UserAlreadyExistException;

    /**
     * Adds an entire place to the database
     *
     * @param propertyID    ID of a property
     * @param userID        ID of a user
     * @param location      name of a location
     * @param capacity      max number of guests of a property
     * @param price         cost of a property
     * @param numberOfRooms number of rooms a property has
     * @param placeType     type of property
     * @throws UserDoesNotExistException        if no user was found
     * @throws InvalidUserTypeException         if the user with that userID is not a host
     * @throws PropertyAlreadyExistException    if there is already a property with that propertyID
     */
    void addEntirePlace(String propertyID, String userID, String location, int capacity,
                        int price, int numberOfRooms, String placeType)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    /**
     * Adds a private room to the database
     *
     * @param propertyID    ID of a property
     * @param userID        ID of a user
     * @param location      name of a location
     * @param capacity      max number of guests of a property
     * @param price         cost of a property
     * @param amenities     amenities guests have access to
     * @throws UserDoesNotExistException        if no user was found
     * @throws InvalidUserTypeException         if the user with that userID is not a host
     * @throws PropertyAlreadyExistException    if there is already a property with that propertyID
     */
    void addPrivateRoom(String propertyID, String userID, String location, int capacity,
                        int price, int amenities)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    /**
     * Adds an amenity to a private room in the database
     *
     * @param propertyID    ID of the property the amenity is being added
     * @param amenity       name of the amenity
     */
    void addAmenity(String propertyID, String amenity);

    /**
     * Confirms a booking in the database and rejects all other bookings with overlapping dates
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return Iterator with the confirmed booking and the bookings that were rejected
     * @throws BookingDoesNotExistException             if no booking was found
     * @throws UserDoesNotExistException                if no user was found
     * @throws InvalidUserTypeException                 if the user with that userID isnt a host
     * @throws InvalidUserTypeForBookingException       if the user isnt the host of the booking
     * @throws CannotExecuteActionInBookingException    if the booking is not in requested state
     */
    Iterator<Booking> confirmBooking(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException;

    /**
     * Adds a booking to the database
     *
     * @param userID        ID of the user
     * @param propertyID    ID of the property
     * @param arrival       time of arrival at property
     * @param departure     time of departure
     * @param numGuests     number of guests of booking
     * @return  Booking that was added
     * @throws UserDoesNotExistException            if no user was found
     * @throws InvalidUserTypeException             if the user with that userID isnt a guest
     * @throws PropertyDoesNotExistException        if no property was found
     * @throws NumGuestsExceedsCapacityException    if the numGuests exceeds the capacity of the property
     * @throws InvalidBookingDatesException         if the booking dates are invalid
     */
    Booking addBooking(String userID, String propertyID, LocalDate arrival,
                       LocalDate departure, int numGuests)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyDoesNotExistException,
            NumGuestsExceedsCapacityException,
            InvalidBookingDatesException;

    /**
     *  Returns the rejected bookings of a given host
     *
     * @param userID    ID of a user
     * @return  Iterator with the rejected bookings of a host
     * @throws UserDoesNotExistException            if no user was found
     * @throws InvalidUserTypeException             if the user with that userID isnt a host
     * @throws UserHasNoBookingsException           if the user has no bookings
     * @throws HostHasNotRejectedBookingsException  if the user has no rejected bookings
     */
    Iterator<booking.Booking> iteratorRejections(String userID)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            UserHasNoBookingsException,
            HostHasNotRejectedBookingsException;

    /**
     *  Rejects a booking in the database
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return  the bookings that was rejected
     * @throws UserDoesNotExistException                if no user was found
     * @throws InvalidUserTypeException                 if the user isnt a guest
     * @throws BookingDoesNotExistException             if no booking was found
     * @throws InvalidUserTypeForBookingException       if the user isnt the host of the property with that booking
     * @throws CannotExecuteActionInBookingException    if the booking is not in requested state
     */
    Booking rejectBooking(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException;

    /**
     * Pays for a booking and rejects or cancels all other bookings with overlapping dates
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return  Iterator with the payed booking, and all the rejected and cancelled bookings
     * @throws BookingDoesNotExistException             if no booking was found
     * @throws UserDoesNotExistException                if no user was found
     * @throws InvalidUserTypeException                 if the user isnt a guest
     * @throws UserNotAllowedToPayBookingException      if the user is not the guest associated with the booking
     * @throws CannotExecuteActionInBookingException    if the booking is not in confirmed state
     */
    Iterator<Booking> pay(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            UserNotAllowedToPayBookingException,
            CannotExecuteActionInBookingException;


    /**
     * Adds a review to a booking in the database
     *
     * @param bookingID         ID of the booking
     * @param userID            ID of the user
     * @param review            the review
     * @param classification    rating of 1 to 5 stars
     * @throws BookingDoesNotExistException             if no booking was found
     * @throws UserDoesNotExistException                if no user was found
     * @throws InvalidUserTypeException                 if the user isnt a guest
     * @throws InvalidUserTypeForBookingException       if the user isnt the guest of that booking
     * @throws CannotExecuteActionInBookingException    if the booking isnt in state paid
     * @throws BookingAlreadyReviewedException          if the booing already has a review
     */
    void addReview(String bookingID, String userID, String review, String classification)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException,
            BookingAlreadyReviewedException;

    /**
     * Returns the guest with that guestID
     *
     * @param guestID ID of that guest
     * @return  the guest with that guestID
     * @throws UserDoesNotExistException    if no user was found
     * @throws InvalidUserTypeException     if the user isnt a guest
     * @throws GuestHasNoBookingsException  if the guest has no bookings
     */
    Guest getGuest(String guestID)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            GuestHasNoBookingsException;

    /**
     * Returns the Iterator of the stays at a property
     *
     * @param propertyID    ID of a property
     * @return  Iterator of the paid bookings of a property
     * @throws PropertyDoesNotExistException    if no property was found
     * @throws PropertyHasNoStaysException      if the property has no paid bookings
     */
    Iterator<Booking> iteratorStaysAtProperty(String propertyID)
            throws PropertyDoesNotExistException,
            PropertyHasNoStaysException;

    /**
     * Returns the Iterator of the properties with a minimum capacity, in a given location
     *
     * @param location  location of the properties
     * @param capacity  minimum capacity of the properties
     * @return  Iterator of the properties that meet the requirements
     * @throws NoPropertyInLocationException    if there are no properties in that location
     */
    Iterator<Property> iteratorPropertiesByCapacity(String location, int capacity)
            throws NoPropertyInLocationException;

    /**
     * Returns the Iterator of the properties in a given location, ordered by rating
     *
     * @param location  location of the properties
     * @return  Iterator of the properties that meet the requirements
     * @throws NoPropertyInLocationException    if there are no properties in that location
     */
    Iterator<Property> iteratorPropertiesByAverage(String location)
            throws NoPropertyInLocationException;

    /**
     * Returns the guest with more locations visited
     * @return the guest with mmore paid bookings
     * @throws NoGlobeTrotterException  if there is no GlobeTrotter
     */
    Guest getGlobeTrotter()
            throws NoGlobeTrotterException;
}
