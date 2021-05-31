package users;

import booking.Booking;
import booking.ComparatorByArrivalDate;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class GuestClass extends UserClassAbs implements Guest {
    private final List<Booking> unpaidBookings;
    private final SortedSet<Booking> paidBookings;
    private final List<Booking> confirmedBookings;
    private final List<Booking> allBookingsByInsertionOrder;
    private final Set<String> visitedLocations;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        unpaidBookings = new LinkedList<>();
        paidBookings = new TreeSet<>(new ComparatorByArrivalDate());
        confirmedBookings = new ArrayList<>();
        allBookingsByInsertionOrder = new ArrayList<>();
        visitedLocations = new HashSet<>();
    }

    public int getBookingsCount() {
        return allBookingsByInsertionOrder.size();
    }

    public double getTotalAmountPaid() {
        double amount = 0.0;
        for (Booking b : paidBookings)
            amount += b.getPaidAmount();
        return amount;
    }

    public int getVisitedLocations() {
        return visitedLocations.size();
    }

    public void addPaidBooking(Booking booking) {
        paidBookings.add(booking);
        unpaidBookings.remove(booking);
        confirmedBookings.remove(booking);
        visitedLocations.add(booking.getProperty().getLocation());
    }

    public void addConfirmedBooking(Booking booking) {
        confirmedBookings.add(booking);
    }

    public void addBooking(Booking booking) {
        unpaidBookings.add(booking);
        allBookingsByInsertionOrder.add(booking);
    }

    public Iterator<Booking> iteratorBookings() {
        return allBookingsByInsertionOrder.iterator();
    }

    @Override
    public Iterator<Booking> pay(Booking booking) {
        ListIterator<Booking> it = unpaidBookings.listIterator();
        List<Booking> temp = new LinkedList<>();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.rejectOrCancel(booking))
                temp.add(b);
        }
        return temp.iterator();
    }

    public boolean isDateInvalid(LocalDate arrival, LocalDate departure) {
        for (Booking b : paidBookings) {
            if (!arrival.isAfter(b.getDepartureDate()))
                return true;
        }
        for (Booking b : confirmedBookings) {
            if (b.dateOverlaps(arrival, departure))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasBooking(Booking booking) {
        return unpaidBookings.contains(booking) || paidBookings.contains(booking);
    }
}
