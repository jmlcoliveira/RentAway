package users;

import booking.Booking;
import exceptions.property.NoPropertiesRegisteredException;
import property.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class HostClass extends UserClassAbs implements Host {

    private final int PROPERTIES_SIZE = 10;
    private final int BOOKINGS_SIZE = 300;
    private final int REJECTED_BOOKINGS_SIZE = 150;

    private final List<Property> properties;
    private final List<Booking> bookings;
    private final List<Booking> rejectedBookings;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>(PROPERTIES_SIZE);
        bookings = new ArrayList<>(BOOKINGS_SIZE);
        rejectedBookings = new ArrayList<>(REJECTED_BOOKINGS_SIZE);
    }

    public int numOfProperties() {
        return properties.size();
    }

    public Iterator<Property> propertyIt() throws NoPropertiesRegisteredException {
        if (properties.size() == 0) throw new NoPropertiesRegisteredException(getIdentifier());
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
