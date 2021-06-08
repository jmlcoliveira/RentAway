package users;

import booking.Booking;
import property.Property;

import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Host extends User {

    /**
     * Gets the number of properties of the host
     *
     * @return number of properties of the host
     */
    int getPropertiesCount();

    /**
     * Returns an Iterator with all the properties of the host
     *
     * @return Iterator with every property of the host
     */
    Iterator<Property> propertyIt();

    /**
     * Gets the sum of bookings of every property of the host
     *
     * @return sum of bookings of every property the host has
     */
    int getBookingsTotal();

    /**
     * Gets the number of rejected bookings of every property of the host
     *
     * @return sum of rejected bookings of every property the host has
     */
    int getRejectedBookings();

    /**
     * Adds a booking of a property of the host
     *
     * @param booking the booking being added
     */
    void addBooking(Booking booking);

    /**
     * Adds a booking that was rejected by the host
     * The insertion order of the booking is kept
     *
     * @param booking the booking being added
     */
    void addRejectedBooking(Booking booking);

    /**
     * Adds a property to the host
     *
     * @param property the property being added
     */
    void addProperty(Property property);

    /**
     * Returns an Iterator with every booking that was rejected by the host
     *
     * @return Iterator with every rejected booking
     */
    Iterator<Booking> iteratorRejectedBookings();

    /**
     * Checks if the host has at least one property
     *
     * @return true if the host has properties
     */
    boolean hasProperties();
}
