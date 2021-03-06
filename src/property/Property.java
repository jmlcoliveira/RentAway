package property;

import booking.Booking;
import booking.exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * Property interface with the methods shared by every property type
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Property {

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
    int getCapacity();

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
     * Checks if the a booking dates overlaps with another booking dates
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
     * Returns a booking from the property or <code>null</code> if the property does not have that booking
     *
     * @param tempBooking a temporary booking object
     * @return the booking from the property or <code>null</code> if the property does not have that booking
     */
    Booking getBooking(Booking tempBooking);

    /**
     * Gets an Iterator with all the paid booking of the property
     *
     * @return Iterator with all the paid bookings of the property
     */
    Iterator<Booking> iteratorPaidBookings();

    /**
     * Gets the host of the property
     *
     * @return the host of the property
     */
    Host getHost();

    /**
     * Pays for a booking in the property, and cancels or rejects all others with overlapping dates
     * Returns an Iterator with the paid booking and the cancelled or rejected bookings
     *
     * @param booking the booking being paid
     * @return Iterator with the paid booking and the cancelled or rejected bookings
     * @throws CannotExecuteActionInBookingException if the booking being paid isn't confirmed
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
     * Adds a review to the property and updates ratings average
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
