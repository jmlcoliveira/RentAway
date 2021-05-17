package users;

import booking.Booking;

import java.util.Iterator;

public interface Guest extends User {

    int numOfBookings();

    int getBookingsTotal();

    int getRequestedBookings();

    int getConfirmedBookings();

    int getRejectedBookings();

    int getCancelledBookings();

    int getPaidBookings();

    double getTotalAmountPaid();

    Iterator<Booking> iteratorBookings();
}
