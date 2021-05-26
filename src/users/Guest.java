package users;

import booking.Booking;

import javax.xml.stream.Location;
import java.time.LocalDate;
import java.util.Iterator;

public interface Guest extends User {

    int getBookingsTotal();

    double getTotalAmountPaid();

    int getVisitedLocations();

    void addPaidBooking(Booking booking);

    void addBooking(Booking booking);

    Iterator<Booking> iteratorBookings();

    LocalDate getLastPaidDepartureDate();

    boolean hasBooking(Booking booking);

    Iterator<Booking> pay(Booking booking);
}
