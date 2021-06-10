package users;

import booking.Booking;
import booking.ComparatorByInsertion;
import property.Property;

import java.util.*;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class HostClass extends UserClassAbs implements Host {

    private final List<Property> properties;
    private final List<Booking> bookings;
    private final SortedSet<Booking> rejectedBookings;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>();
        bookings = new ArrayList<>();
        rejectedBookings = new TreeSet<>(new ComparatorByInsertion(false));
    }

    public final int getPropertiesCount() {
        return properties.size();
    }

    public final boolean hasProperties() {
        return properties.size() > 0;
    }

    public final Iterator<Property> propertyIt() {
        return properties.iterator();
    }

    public final int getBookingsTotal() {
        return bookings.size();
    }

    public final int getRejectedBookings() {
        return rejectedBookings.size();
    }

    public final void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public final void addRejectedBooking(Booking booking) {
        rejectedBookings.add(booking);
    }

    public final void addProperty(Property property) {
        properties.add(property);
    }

    public final Iterator<Booking> iteratorRejectedBookings() {
        return rejectedBookings.iterator();
    }
}
