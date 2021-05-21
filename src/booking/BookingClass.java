package booking;

import exceptions.CannotConfirmBookingException;
import property.Property;
import review.Review;
import users.Guest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingClass implements Booking{
    private final String identifier;
    private final Guest guest;
    private final Property property;
    private Review review;
    private int numberOfGuests;
    private LocalDate arrivalDate, departureDate;
    private BookingState state;

    public BookingClass(String idNumber, Guest guest, Property property, int numberOfGuests,
                        LocalDate arrivalDate, LocalDate departureDate){
        this.identifier = property.getIdentifier()+ "-" + idNumber;
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

    public String getPropertyID() {
        return property.getIdentifier();
    }

    public Guest getGuest() {
        return guest;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getPrice() {
        return property.getPrice()*(departureDate.compareTo(arrivalDate));
    }

    public BookingState getState() {
        return state;
    }

    public Property getProperty() {
        return property;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public boolean isPaid() {
        return (state == BookingState.PAID || state == BookingState.REVIEWED);
    }

    @Override
    public void confirm() throws CannotConfirmBookingException {
        if(!this.state.equals(BookingState.REQUESTED))
            throw new CannotConfirmBookingException(identifier, state.getStateValue());
        this.state = BookingState.CONFIRMED;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Booking)) return false;
        return ((Booking)o).getIdentifier().equals(getIdentifier());
    }
}
