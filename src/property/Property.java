package property;

import booking.Booking;

import java.time.LocalDate;
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

    PropertyType getType();

    void addBooking(Booking b);

    boolean hasBooking();

    List<Booking> getBookings();

    LocalDate getPropertyLastPaidDepartureDate();
}
