package booking;

import property.Property;
import review.Review;
import users.Guest;

import java.time.LocalDateTime;

public class BookingClass implements Booking{
    private final String identifier;
    private final Guest guest;
    private final Property property;
    private Review review;
    private int numberOfGuests;
    private LocalDateTime arrivalDate, departureDate;
    private BookingState state;

    public BookingClass(String identifier, Guest guest, Property property, int numberOfGuests,
                        LocalDateTime arrivalDate, LocalDateTime departureDate){
        this.identifier = identifier;
        this.guest = guest;
        this.property = property;
        this.numberOfGuests = numberOfGuests;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        state = BookingState.REQUESTED;
    }

    public String getIdentifier() {
        return identifier;
    }
}
