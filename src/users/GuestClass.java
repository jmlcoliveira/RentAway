package users;

import booking.Booking;
import booking.BookingClass;
import booking.BookingState;

import java.util.*;

public class GuestClass extends UserClassAbs implements Guest {
    private final List<Booking> bookings;
    private final Set<String> visitedLocations;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        bookings = new ArrayList<>();
        visitedLocations = new HashSet<>();
    }

    public int getBookingsTotal() {
        return bookings.size();
    }

    public int getRequestedBookings() {
        return getBookingsCountByState(BookingState.REQUESTED);
    }

    public int getConfirmedBookings() {
        return getBookingsCountByState(BookingState.CONFIRMED);
    }

    public int getRejectedBookings() {
        return getBookingsCountByState(BookingState.REJECTED);
    }

    public int getCancelledBookings() {
        return getBookingsCountByState(BookingState.CANCELLED);
    }

    public int getPaidBookings() {
        return getBookingsCountByState(BookingState.PAID) + getBookingsCountByState(BookingState.REVIEWED);
    }

    public double getTotalAmountPaid() {
        double amount = 0.0;
        for (Booking b : bookings)
            if (b.isPaid())
                amount += b.getPrice();
        return amount;
    }

    public int getVisitedLocations() {
        return visitedLocations.size();
    }

    private int getBookingsCountByState(BookingState bs) {
        int count = 0;
        for (Booking b : bookings)
            if (b.getState() == bs)
                count++;
        return count;
    }

    public Iterator<Booking> iteratorBookings() {
        return bookings.iterator();
    }
}
