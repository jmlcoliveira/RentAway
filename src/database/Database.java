package database;

import booking.Booking;
import booking.exceptions.*;
import property.Property;
import property.exceptions.NumGuestsExceedsCapacityException;
import property.exceptions.PropertyAlreadyExistException;
import property.exceptions.PropertyDoesNotExistException;
import users.Guest;
import users.User;
import users.exceptions.InvalidUserTypeException;
import users.exceptions.NoGlobeTrotterException;
import users.exceptions.UserAlreadyExistException;
import users.exceptions.UserDoesNotExistException;

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
     */
    Iterator<User> iteratorUsers();

    boolean hasUsers();

    /**
     * Returns an Iterator with every property of a given host
     *
     * @param id the ID of the host
     * @return Iterator with the properties of the host
     */
    Iterator<Property> iteratorPropertiesByHost(String id);

    /**
     * Returns <code>true</code> if the host is valid and has properties, <code>false</code> otherwise
     *
     * @param userID id of an user
     * @return <code>true</code> if the host is valid and has properties, <code>false</code> otherwise
     * @throws UserDoesNotExistException if no user was found
     * @throws InvalidUserTypeException  if the user with that userID is not a host
     */
    boolean hostHasProperties(String userID) throws UserDoesNotExistException, InvalidUserTypeException;

    /**
     * Adds a guest to the database
     *
     * @param identifier  ID of the guest
     * @param name        name of the guest
     * @param nationality nationality of the guest
     * @param email       email of the guest
     * @throws UserAlreadyExistException if there is already a user with the same identifier
     */
    void addGuest(String identifier, String name, String nationality, String email)
            throws UserAlreadyExistException;

    /**
     * Adds a host to the database
     *
     * @param identifier  ID of the host
     * @param name        name of the host
     * @param nationality nationality of the host
     * @param email       email of the host
     * @throws UserAlreadyExistException if there is already a user with the same identifier
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
     * @throws UserDoesNotExistException     if no user was found
     * @throws InvalidUserTypeException      if the user with that userID is not a host
     * @throws PropertyAlreadyExistException if there is already a property with that propertyID
     */
    void addEntirePlace(String propertyID, String userID, String location, int capacity,
                        double price, int numberOfRooms, String placeType)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    /**
     * Adds a private room to the database
     *
     * @param propertyID ID of a property
     * @param userID     ID of a user
     * @param location   name of a location
     * @param capacity   max number of guests of a property
     * @param price      cost of a property
     * @param amenities  amenities guests have access to
     * @throws UserDoesNotExistException     if no user was found
     * @throws InvalidUserTypeException      if the user with that userID is not a host
     * @throws PropertyAlreadyExistException if there is already a property with that propertyID
     */
    void addPrivateRoom(String propertyID, String userID, String location, int capacity,
                        double price, int amenities)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyAlreadyExistException;

    /**
     * Adds an amenity to a private room in the database
     *
     * @param propertyID ID of the property the amenity is being added
     * @param amenity    name of the amenity
     */
    void addAmenity(String propertyID, String amenity);

    /**
     * Confirms a booking in the database and rejects all other bookings with overlapping dates
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return Iterator with the confirmed booking and the bookings that were rejected
     * @throws BookingDoesNotExistException          if no booking was found
     * @throws UserDoesNotExistException             if no user was found
     * @throws InvalidUserTypeException              if the user with that userID isn't a host
     * @throws InvalidUserTypeForBookingException    if the user isn't the host of the booking
     * @throws CannotExecuteActionInBookingException if the booking is not in requested state
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
     * @param userID     ID of the user
     * @param propertyID ID of the property
     * @param arrival    time of arrival at property
     * @param departure  time of departure
     * @param numGuests  number of guests of booking
     * @return Booking that was added
     * @throws UserDoesNotExistException         if no user was found
     * @throws InvalidUserTypeException          if the user with that userID isn't a guest
     * @throws PropertyDoesNotExistException     if no property was found
     * @throws NumGuestsExceedsCapacityException if the numGuests exceeds the capacity of the property
     * @throws InvalidBookingDatesException      if the booking dates are invalid
     */
    Booking addBooking(String userID, String propertyID, LocalDate arrival,
                       LocalDate departure, int numGuests)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            PropertyDoesNotExistException,
            NumGuestsExceedsCapacityException,
            InvalidBookingDatesException;

    /**
     * Returns the rejected bookings of a given host
     *
     * @param userID ID of a user
     * @return Iterator with the rejected bookings of a host
     * @throws UserHasNoBookingsException if the user has no bookings
     * @pre hostHasRejectedBookings(userID)
     */
    Iterator<Booking> iteratorRejections(String userID) throws UserHasNoBookingsException;

    /**
     * Checks if the host has rejected bookings
     *
     * @param userID ID of a user
     * @return <code>true</code> if there is at least one rejected booking
     * @throws UserDoesNotExistException  if no user was found
     * @throws InvalidUserTypeException   if the user with that userID isn't a host
     * @throws UserHasNoBookingsException if the user has no bookings
     */
    boolean hostHasRejectedBookings(String userID)
            throws UserDoesNotExistException,
            InvalidUserTypeException,
            UserHasNoBookingsException;

    /**
     * Rejects a booking
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return the bookings that was rejected
     * @throws UserDoesNotExistException             if no user was found
     * @throws InvalidUserTypeException              if the user isn't a guest
     * @throws BookingDoesNotExistException          if no booking was found
     * @throws InvalidUserTypeForBookingException    if the user isn't the host of the property with that booking
     * @throws CannotExecuteActionInBookingException if the booking is not in requested state
     */
    Booking rejectBooking(String bookingID, String userID)
            throws BookingDoesNotExistException,
            UserDoesNotExistException,
            InvalidUserTypeException,
            InvalidUserTypeForBookingException,
            CannotExecuteActionInBookingException;

    /**
     * Pays for a booking and returns an iterator with the payed booking,
     * and all the bookings which had to be rejected or cancelled
     *
     * @param bookingID ID of the booking
     * @param userID    ID of the user
     * @return Iterator with the payed booking, and all the rejected and cancelled bookings
     * @throws BookingDoesNotExistException          if no booking was found
     * @throws UserDoesNotExistException             if no user was found
     * @throws InvalidUserTypeException              if the user isn't a guest
     * @throws UserNotAllowedToPayBookingException   if the user is not the guest associated with the booking
     * @throws CannotExecuteActionInBookingException if the booking is not in confirmed state
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
     * @param bookingID      ID of the booking
     * @param userID         ID of the user
     * @param review         the review
     * @param classification rating of 1 to 5 stars
     * @throws BookingDoesNotExistException          if no booking was found
     * @throws UserDoesNotExistException             if no user was found
     * @throws InvalidUserTypeException              if the user isn't a guest
     * @throws InvalidUserTypeForBookingException    if the user isn't the guest of that booking
     * @throws CannotExecuteActionInBookingException if the booking isn't in state paid
     * @throws BookingAlreadyReviewedException       if the booing already has a review
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
     * @return the guest with that guestID
     * @pre guestHasBookings(guestID)
     */
    Guest getGuest(String guestID);

    /**
     * Checks if the guest has bookings
     *
     * @param guestID ID of that guest
     * @return true if the guest has at least one booking
     * @throws UserDoesNotExistException if no user was found
     * @throws InvalidUserTypeException  if the user isn't a guest
     */
    boolean guestHasBookings(String guestID)
            throws UserDoesNotExistException,
            InvalidUserTypeException;

    /**
     * Returns the Iterator of the stays at a property
     *
     * @param propertyID ID of a property
     * @return Iterator of the paid bookings of a property
     */
    Iterator<Booking> iteratorStaysAtProperty(String propertyID);

    /**
     * Checks if the property has at least one paid booking
     *
     * @param propertyID ID of the property
     * @return true if the property has at least one stay
     * @throws PropertyDoesNotExistException if no property was found
     */
    boolean propertyHasStays(String propertyID)
            throws PropertyDoesNotExistException;

    /**
     * Returns the Iterator of the properties with a minimum capacity, in a given location
     *
     * @param location location of the properties
     * @param capacity minimum capacity of the properties
     * @return Iterator of the properties that meet the requirements
     * @pre hasProperty(location, capacity)
     */
    Iterator<Property> iteratorPropertiesByCapacity(String location, int capacity);

    /**
     * Returns the Iterator of the properties in a given location, ordered by rating
     *
     * @param location location of the properties
     * @return Iterator of the properties that meet the requirements
     * @pre hasProperty(location)
     */
    Iterator<Property> iteratorPropertiesByAverage(String location);

    /**
     * Checks if the database has a property in the given location, with a minimum amount of capacity
     *
     * @param location  the desired location
     * @param numGuests the minimum capacity
     * @return <code>true</code> if there is at least one property that fits the criteria
     */
    boolean hasProperty(String location, int numGuests);

    /**
     * Checks if the database has a property in the given location
     *
     * @param location the desired location
     * @return <code>true</code> if there is at least one property that fits the criteria
     */
    boolean hasProperty(String location);

    /**
     * Returns the guest with more locations visited
     *
     * @return the guest with more paid bookings
     * @throws NoGlobeTrotterException if there is no GlobeTrotter
     */
    Guest getGlobeTrotter()
            throws NoGlobeTrotterException;
}
