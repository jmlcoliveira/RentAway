package property;

import booking.Booking;
import booking.exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Property extends Comparable<Property> {

    /**
     * Gets the ID of the property
     *
     * @return ID of the property
     */
    String getIdentifier();

    /**
     * Gets the location of the property
     *
     * @return location of the property
     */
    String getLocation();

    /**
     * Gets the capacity of the property
     *
     * @return capacity of the property
     */
    int getGuestsCapacity();

    /**
     * Gets the price of the property
     *
     * @return price of the property
     */
    double getPrice();

    /**
     * Gets the number of bookings of the property
     *
     * @return number of bookings of the property
     */
    int getBookingCount();

    /**
     * gets the number of paid bookings of the property
     *
     * @return number of paid bookings of the property
     */
    int getPaidBookingCount();

    /**
     * Gets the number of reviews the property has
     *
     * @return number of reviews of the property
     */
    int getReviewCount();

    /**
     * Gets the average rating of the reviews of the property
     *
     * @return average rating of the reviews of the property
     */
    double getAverageRating();

    /**
     * Adds a paid booking to the property
     *
     * @param booking the paid booking being added
     */
    void addPaidBooking(Booking booking);

    /**
     * Adds a confirmed booking to the property
     *
     * @param booking the confirmed booking being added
     */
    void addConfirmedBooking(Booking booking);

    /**
     * Adds a booking to the property
     *
     * @param booking the booking being added
     */
    void addBooking(Booking booking);

    /**
     * Checks if the a booking's dates overlap with another booking's dates
     *
     * @param booking the bookings being compared
     */
    boolean bookingOverlaps(Booking booking);

    /**
     * Gets the type of the property
     *
     * @return type of property
     */
    PropertyType getType();

    /**
     * Gets a booking from the property
     *
     * @param b the booking
     * @return the booking b
     * @pre the property has the booking
     */
    Booking getBooking(Booking b);

    /**
     * Gets an Iterator with all the paid booking of the property
     *
     * @return Iterator with all the paid bookings of the property
     */
    Iterator<Booking> iteratorPaidBookings();

    /**
     * Gets the host of the property
     *
     * @return the host of the proprety
     */
    Host getHost();

    /**
     * Pays for a booking in the property, and cancels or rejects all others with overlapping dates
     *
     * @param booking the booking being paid
     * @return  Iterator with the paid booking and the cancelled or rejected bookings
     * @throws CannotExecuteActionInBookingException    if the booking being paid isn't confirmed
     */
    Iterator<Booking> pay(Booking booking) throws CannotExecuteActionInBookingException;

    /**
     * Checks if the date is invalid
     *
     * @param arrival   the arrival date
     * @param departure the departure date
     * @return  true the date is invalid
     */
    boolean isDateInvalid(LocalDate arrival, LocalDate departure);

    /**
     * Confirms for a booking in the property, and cancels or rejects all others with overlapping dates
     *
     * @param booking the booking being confirmed
     * @return  Iterator with the paid booking and the cancelled or rejected bookings
     * @throws CannotExecuteActionInBookingException    if the booking being paid isn't requested
     */
    Iterator<Booking> confirmBooking(Booking booking) throws CannotExecuteActionInBookingException;

    /**
     * Adds a review to the property
     *
     * @param review the review being added
     */
    void addReview(Review review);

    /**
     * Checks if the property has paid bookings
     *
     * @return true if the property has at least one paid booking
     */
    boolean hasStays();
}
