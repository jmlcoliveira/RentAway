package users;

import booking.Booking;
import booking.BookingClass;
import booking.BookingState;
import booking.ComparatorByArrivalDate;

import java.time.LocalDate;
import java.util.*;

public class GuestClass extends UserClassAbs implements Guest {
    private final List<Booking> bookings;
    private final SortedSet<Booking> paidBookings;
    private final Set<String> visitedLocations;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        bookings = new LinkedList<>();
        paidBookings = new TreeSet<>(new ComparatorByArrivalDate());
        visitedLocations = new HashSet<>();
    }

    public int getBookingsTotal() {
        return bookings.size();
    }

    public double getTotalAmountPaid() {
        double amount = 0.0;
        for (Booking b : paidBookings)
                amount += b.getPrice();
        return amount;
    }

    public int getVisitedLocations() {
        return visitedLocations.size();
    }

    public void addPaidBooking(Booking booking) {
        paidBookings.add(booking);
        visitedLocations.add(booking.getProperty().getLocation());
    }

    public Iterator<Booking> iteratorBookings() {
        return bookings.iterator();
    }
}
