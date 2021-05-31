package users;

import booking.Booking;
import property.Property;

import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Host extends User {

    int numOfProperties();

    Iterator<Property> propertyIt();

    int getBookingsTotal();

    int getRejectedBookings();

    void addBooking(Booking booking);

    void addRejectedBooking(Booking booking);

    void addProperty(Property property);

    Iterator<Booking> iteratorRejectedBookings();

    boolean hasProperties();
}
