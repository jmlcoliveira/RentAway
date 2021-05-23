package property;

import booking.Booking;
import exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public interface Property extends Comparable<Property> {

    PropertyType type();

    String getIdentifier();

    String getLocation();

    int getGuestsCapacity();

    int getPrice();

    int getBookingCount();

    int getPaidBookingCount();

    int getReviewCount();

    double getAverageRating();

    double getTotalPayment();

    void addPaidBooking(Booking b);

    PropertyType getType();

    List<Booking> getBookings();

    Host getHost();

    LocalDate getPropertyLastPaidDepartureDate();
}
