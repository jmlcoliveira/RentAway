package booking;

import property.Property;
import users.Guest;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
