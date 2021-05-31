package users;

import booking.Booking;
import property.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class HostClass extends UserClassAbs implements Host {

    private final List<Property> properties;
    private final List<Booking> bookings;
    private final List<Booking> rejectedBookings;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>();
        bookings = new ArrayList<>();
        rejectedBookings = new ArrayList<>();
    }

    public int numOfProperties() {
        return properties.size();
    }

    @Override
    public boolean hasProperties() {
        return properties.size() > 0;
    }

    public Iterator<Property> propertyIt() {
        return properties.iterator();
    }

    public int getBookingsTotal() {
        return bookings.size();
    }

    public int getRejectedBookings() {
        return rejectedBookings.size();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void addRejectedBooking(Booking booking) {
        int i = bookings.indexOf(booking);
        for (Booking next : rejectedBookings) {
            if (i < bookings.indexOf(next)) {
                rejectedBookings.add(rejectedBookings.indexOf(next), booking);
                return;
            }
        }
        rejectedBookings.add(booking);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public Iterator<Booking> iteratorRejectedBookings() {
        return rejectedBookings.iterator();
    }
}
