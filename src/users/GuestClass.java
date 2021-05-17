package users;

import booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class GuestClass extends UserClassAbs implements Guest{
    private List<Booking> bookings;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        bookings = new ArrayList<>();
    }

    public int numOfBookings() {
        return bookings.size();
    }

    public int getBookingsTotal() {
        return 0;
    }

    public int getRequestedBookings() {
        return 0;
    }

    public int getConfirmedBookings() {
        return 0;
    }

    public int getRejectedBookings() {
        return 0;
    }

    public int getCancelledBookings() {
        return 0;
    }

    public int getPaidBookings() {
        return 0;
    }

    public double getTotalAmountPaid() {
        return 0;
    }
}
