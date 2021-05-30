package users;

import booking.Booking;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Guest extends User {

    int getBookingsCount();

    double getTotalAmountPaid();

    int getVisitedLocations();

    void addPaidBooking(Booking booking);

    void addBooking(Booking booking);

    Iterator<Booking> iteratorBookings();

    LocalDate getLastPaidDepartureDate();

    boolean hasBooking(Booking booking);

    Iterator<Booking> pay(Booking booking);
}
