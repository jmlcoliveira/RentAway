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
    private final List<Booking> allBookingsByInsertionOrder;
    private final Set<String> visitedLocations;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        unpaidBookings = new ArrayList<>();
        paidBookings = new TreeSet<>(new ComparatorByArrivalDate());
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
        visitedLocations.add(booking.getProperty().getLocation());
    }

    public void addBooking(Booking booking) {
        unpaidBookings.add(booking);
        allBookingsByInsertionOrder.add(booking);
    }

    public Iterator<Booking> iteratorBookings() {
        return allBookingsByInsertionOrder.iterator();
    }

    public LocalDate getLastPaidDepartureDate() {
        if (paidBookings.size() == 0) return null;
        LocalDate date = paidBookings.last().getDepartureDate();
        for (Booking b : paidBookings) {
            LocalDate d = b.getDepartureDate();
            if (d.isAfter(date))
                date = d;
        }
        return date;
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

    @Override
    public boolean hasBooking(Booking booking) {
        return unpaidBookings.contains(booking) || paidBookings.contains(booking);
    }
}
