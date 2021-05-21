package users;

import booking.Booking;

import javax.xml.stream.Location;
import java.time.LocalDate;
import java.util.Iterator;

public interface Guest extends User {

    int getBookingsTotal();

    int getRequestedBookings();

    int getConfirmedBookings();

    int getRejectedBookings();

    int getCancelledBookings();

    int getPaidBookings();

    double getTotalAmountPaid();

    int getVisitedLocations();

    Iterator<Booking> iteratorBookings();

    LocalDate getLastDepartureDate();
}
