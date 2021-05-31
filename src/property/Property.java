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

    String getIdentifier();

    String getLocation();

    int getGuestsCapacity();

    double getPrice();

    int getBookingCount();

    int getPaidBookingCount();

    int getReviewCount();

    double getAverageRating();

    void addPaidBooking(Booking b);

    void addConfirmedBooking(Booking booking);

    void addBooking(Booking booking);

    boolean bookingOverlaps(Booking booking);

    PropertyType getType();

    Booking getBooking(Booking b);

    Iterator<Booking> iteratorPaidBookings();

    Host getHost();

    Iterator<Booking> pay(Booking booking) throws CannotExecuteActionInBookingException;

    boolean isDateInvalid(LocalDate arrival, LocalDate departure);

    Iterator<Booking> confirmBooking(Booking booking) throws CannotExecuteActionInBookingException;

    void addReview(Review review);

    boolean hasStays();
}
