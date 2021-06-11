package users;

import booking.Booking;
import booking.ComparatorByInsertion;
import property.Property;

import java.util.*;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class HostClass extends UserClassAbs implements Host {

    /**
     * List which contains every property owned by the host
     */
    private final List<Property> properties;
    /**
     * List wich contains every booking made in a property owned by the host
     */
    private final List<Booking> bookings;
    /**
     * List which contains every rejected booking made in a property owned bu the host
     */
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
