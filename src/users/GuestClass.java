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

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public Iterator<Booking> iteratorBookings() {
        return bookings.iterator();
    }

    public LocalDate getLastDepartureDate() {
        if(paidBookings.size()==0) return null;
        LocalDate date = paidBookings.last().getDepartureDate();
        for (Booking b : paidBookings){
            LocalDate d = b.getDepartureDate();
            if (d.isAfter(date))
                date = d;
        }
        return date;
    }

    @Override
    public Iterator<Booking> pay(Booking booking) {
        ListIterator<Booking> it = bookings.listIterator();
        while (it.hasPrevious()) {
            Booking b = it.previous();
            if (b.rejectOrCancel(booking))
                bookings.add(b);
        }
        return bookings.iterator();
    }

    @Override
    public boolean hasBooking(Booking booking) {
        return bookings.contains(booking);
    }
}
