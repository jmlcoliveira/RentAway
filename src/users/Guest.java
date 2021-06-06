package users;

import booking.Booking;

import java.time.LocalDate;
import java.util.Iterator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Guest extends User {

    /**
     * Gets the number of bookings of the guest
     *
     * @return number of bookings of the guest
     */
    int getBookingsCount();

    /**
     * Gets the total amount spent on bookings by the guest
     *
     * @return  the amount spent
     */
    double getTotalAmountPaid();

    /**
     * Gets the number of visited locations by the guest
     *
     * @return the number of visited locations
     */
    int getVisitedLocations();

    //todo
    /**
     * Adds a paid booking to the guest
     *
     * @param booking the booking being paid
     */
    void addPaidBooking(Booking booking);

    /**
     * Adds a confirmed booking to the guest
     *
     * @param booking  the booking being confirmed
     */
    void addConfirmedBooking(Booking booking);

    /**
     * Adds a booking to the guest
     *
     * @param booking   the booking being added
     */
    void addBooking(Booking booking);

    /**
     * Returns an Iterator of all the bookings of the guest
     *
     * @return  Iterator of the bookings of the guest
     */
    Iterator<Booking> iteratorBookings();

    /**
     * Checks if the guest has a booking
     *
     * @param booking   the booking
     * @return  true if the guest has that booking
     */
    boolean hasBooking(Booking booking);

    /**
     * Pays a booking of the guest and returns an iterator with the bookings which where affected by this action
     *
     * @param booking the booking being paid
     * @return an iterator with the bookings which where affected by this action
     */
    Iterator<Booking> pay(Booking booking);

    /**
     * Checks if the date of a booking is invalid
     *
     * @param arrival   arrival date of the booking
     * @param departure departure date of the booking
     * @return <code>true</code> if the date is invalid
     */
    boolean isDateInvalid(LocalDate arrival, LocalDate departure);
}
