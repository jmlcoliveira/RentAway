package booking;

import booking.exceptions.BookingAlreadyReviewedException;
import booking.exceptions.CannotExecuteActionInBookingException;
import property.Property;
import users.Guest;
import users.Host;

import java.time.LocalDate;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Booking {

    /**
     * Reviews a booking
     *
     * @param comment a comment on the stay
     * @param classification    a rating from 1 to 5
     * @throws BookingAlreadyReviewedException  if the booking was already reviewed
     */
    void review(String comment, String classification) throws BookingAlreadyReviewedException;

    /**
     * Gets the ID of the booking
     *
     * @return ID of the booking
     */
    String getIdentifier();

    /**
     * Gets the guest that made the booking
     *
     * @return guest of booking
     */
    Guest getGuest();

    /**
     * Gets the number of guests of the booking
     *
     * @return number of guests of the booking
     */
    int getNumberOfGuests();

    /**
     * Gets the price of the booking
     *
     * @return price of booking
     */
    double getPaidAmount();

    /**
     * Gets the state of the booking
     *
     * @return state of booking
     */
    BookingState getState();

    /**
     * Gets the property of the booking
     *
     * @return property of booking
     */
    Property getProperty();

    /**
     * Gets the arrival date of the booking
     *
     * @return  date the guest arrives at property
     */
    LocalDate getArrivalDate();

    /**
     * Gets the departure date of the booking
     *
     * @return  date the guest leaves the property
     */
    LocalDate getDepartureDate();

    /**
     * Checks if the booking is paid
     *
     * @return  true if the booking is paid
     */
    boolean isPaid();

    /**
     * Confirms the booking
     *
     * @throws CannotExecuteActionInBookingException if the booking isn't in state requested
     */
    void confirm() throws CannotExecuteActionInBookingException;

    /**
     * Always confirms the booking
     */
    void forceConfirm();

    /**
     * Pays for the booking
     *
     * @throws CannotExecuteActionInBookingException if the booking isn't in state confirmed
     */
    void pay() throws CannotExecuteActionInBookingException;

    /**
     * Rejects the booking
     */
    void reject();

    /**
     * If the departure date of this booking is before the arrival date of the booking b:
     * if booking b is in requested status, rejects it
     * if booking b is in confirmed status, cancels it
     *
     * @param b the booking
     * @return  true if it changed b's status
     */
    boolean rejectOrCancel(Booking b);

    /**
     * Checks if the date of this booking overlaps with another booking's dates
     *
     * @param arrival date of arrival
     * @param departure date of departure
     * @return  true if they overlap
     */
    boolean dateOverlaps(LocalDate arrival, LocalDate departure);

    /**
     * Gets the host of the property of the booking
     *
     * @return  Host of the property of the booking
     */
    Host getHost();
}
