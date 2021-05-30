package users;

import booking.Booking;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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

    boolean hasBooking(Booking booking);

    List<Booking> pay(Booking booking);

    boolean dateOverlaps(LocalDate arrival, LocalDate departure);
}
