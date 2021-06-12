package users;

import booking.Booking;

import java.time.LocalDate;
import java.util.*;

/**
 * Type of user that can rent properties
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class GuestClass extends UserClassAbs implements Guest {

    /**
     * List containing all bookings which are not paid or reviewed
     */
    private final List<Booking> unpaidBookings;

    /**
     * List containing all bookings which are confirmed
     */
    private final List<Booking> confirmedBookings;

    /**
     * List containing all bookings, by the order the guest made them
     */
    private final List<Booking> allBookingsByInsertionOrder;

    /**
     * Set with every location the guest visited
     */
    private final Set<String> visitedLocations;

    /**
     * Total money spent by this guest
     */
    private double totalPaidAmount;

    /**
     * Date of departure of the last paid booking
     */
    private LocalDate currentDate;

    /**
     * Constructor method
     *
     * @param identifier  ID of the guest
     * @param name        Name of the guest
     * @param nationality Nationality of the guest
     * @param email       Email of the guest
     */
    public GuestClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        unpaidBookings = new LinkedList<>();
        confirmedBookings = new ArrayList<>();
        allBookingsByInsertionOrder = new ArrayList<>();
        visitedLocations = new HashSet<>();
        totalPaidAmount = 0;
        currentDate = null;
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
        unpaidBookings.remove(booking);
        confirmedBookings.remove(booking);
        visitedLocations.add(booking.getProperty().getLocation());
        totalPaidAmount += booking.getPaidAmount();
        currentDate = booking.getDepartureDate();
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

    public final Iterator<Booking> pay(Booking booking) {
        Iterator<Booking> it = unpaidBookings.iterator();
        List<Booking> temp = new ArrayList<>(unpaidBookings.size());
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.rejectedOrCanceled(booking))
                temp.add(b);
        }
        return temp.iterator();
    }

    public final boolean isDateInvalid(LocalDate arrival, LocalDate departure) {
        if (currentDate != null)
            if (!arrival.isAfter(currentDate))
                return true;

        for (Booking b : confirmedBookings) {
            if (b.dateOverlaps(arrival, departure))
                return true;
        }
        return false;
    }

    public boolean hasBooking(Booking booking) {
        return allBookingsByInsertionOrder.contains(booking);
    }
}
