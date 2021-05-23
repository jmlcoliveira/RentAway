package booking;

import exceptions.*;
import property.Property;
import users.Guest;

import java.time.LocalDate;

public interface Booking {

    String getIdentifier();

    String getPropertyID();

    Guest getGuest();

    int getNumberOfGuests();

    double getPrice();

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
}
