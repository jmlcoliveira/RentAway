package booking;

import exceptions.booking.BookingAlreadyReviewedException;
import exceptions.booking.CannotExecuteActionInBookingException;
import property.Property;
import users.Guest;
import users.Host;

import java.time.LocalDate;

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

    void pay() throws CannotExecuteActionInBookingException;

    void reject();

    void cancel();

    boolean rejectOrCancel(Booking b);

    boolean dateOverlaps(Booking booking);

    Host getHost();
}
