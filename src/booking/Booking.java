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

    void review(String comment, String classification) throws BookingAlreadyReviewedException;

    String getIdentifier();

    String getPropertyID();

    Guest getGuest();

    int getNumberOfGuests();

    double getPaidAmount();

    BookingState getState();

    Property getProperty();

    LocalDate getArrivalDate();

    LocalDate getDepartureDate();

    boolean isPaid();

    void confirm() throws CannotExecuteActionInBookingException;

    void forceConfirm();

    void pay() throws CannotExecuteActionInBookingException;

    void reject();

    boolean rejectOrCancel(Booking b);

    boolean dateOverlaps(LocalDate arrival, LocalDate departure);

    Host getHost();
}
