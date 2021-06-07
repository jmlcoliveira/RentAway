package users;

import booking.Booking;
import booking.ComparatorByNameDesc;

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
    private double totalPaidAmount;

    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        unpaidBookings = new LinkedList<>();
        paidBookings = new TreeSet<>(new ComparatorByNameDesc());
        confirmedBookings = new ArrayList<>();
        allBookingsByInsertionOrder = new ArrayList<>();
        visitedLocations = new HashSet<>();
        totalPaidAmount = 0;
    }

    public final int getBookingsCount() {
        return allBookingsByInsertionOrder.size();
    }

    public final double getTotalAmountPaid() {
        return totalPaidAmount;
    }

    public final int getVisitedLocations() {
        return visitedLocations.size();
    }

    public final void addPaidBooking(Booking booking) {
        paidBookings.add(booking);
        unpaidBookings.remove(booking);
        confirmedBookings.remove(booking);
        visitedLocations.add(booking.getProperty().getLocation());
        totalPaidAmount += booking.getPaidAmount();
    }

    public final void addConfirmedBooking(Booking booking) {
        confirmedBookings.add(booking);
    }

    public final void addBooking(Booking booking) {
        unpaidBookings.add(booking);
        allBookingsByInsertionOrder.add(booking);
    }

    public final Iterator<Booking> iteratorBookings() {
        return allBookingsByInsertionOrder.iterator();
    }

    @Override
    public final Iterator<Booking> pay(Booking booking) {
        ListIterator<Booking> it = unpaidBookings.listIterator();
        List<Booking> temp = new LinkedList<>();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.rejectedOrCanceled(booking))
                temp.add(b);
        }
        return temp.iterator();
    }

    public final boolean isDateInvalid(LocalDate arrival, LocalDate departure) {
        if (!paidBookings.isEmpty())
            if (!arrival.isAfter(paidBookings.first().getDepartureDate())) //its only needed to check the first booking, because is the one with the recent departure date
                return true;

        for (Booking b : confirmedBookings) {
            if (b.dateOverlaps(arrival, departure))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasBooking(Booking booking) {
        if (paidBookings.contains(booking)) return true;
        return unpaidBookings.contains(booking);
    }
}
