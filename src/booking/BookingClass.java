package booking;

import commands.Command;
import exceptions.booking.BookingAlreadyReviewedException;
import exceptions.booking.CannotExecuteActionInBookingException;
import property.Property;
import review.Rating;
import review.Review;
import review.ReviewClass;
import users.Guest;
import users.Host;

import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class BookingClass implements Booking {
    private final String identifier;
    private final Guest guest;
    private final Property property;
    private Review review;
    private final int numberOfGuests;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private BookingState state;

    public BookingClass(String bookingID, Guest guest, Property property, int numberOfGuests,
                        LocalDate arrivalDate, LocalDate departureDate) {
        this.identifier = bookingID;
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

    public double getPaidAmount() {
        return isPaid() ? property.getPrice() * Duration.between(arrivalDate.atStartOfDay(),
                departureDate.atStartOfDay()).toDays() : 0.00;
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
    public void review(String comment, String classification) throws BookingAlreadyReviewedException {
        if (review != null) throw new BookingAlreadyReviewedException(this.identifier);
        review = new ReviewClass(comment, Rating.valueOf(classification.toUpperCase()));
        property.addReview(review);
        state = BookingState.REVIEWED;
    }

    @Override
    public void confirm() throws CannotExecuteActionInBookingException {
        if (!this.state.equals(BookingState.REQUESTED))
            throw new CannotExecuteActionInBookingException(
                    Command.CONFIRM.getCommand(),
                    identifier,
                    state.getStateValue());
        this.state = BookingState.CONFIRMED;
    }

    public void forceConfirm() {
        this.state = BookingState.CONFIRMED;
    }

    @Override
    public void pay() throws CannotExecuteActionInBookingException {
        if (!this.state.equals(BookingState.CONFIRMED))
            throw new CannotExecuteActionInBookingException(Command.PAY.getCommand(), identifier,
                    state.getStateValue());
        this.state = BookingState.PAID;
    }

    public void reject() {
        state = BookingState.REJECTED;
        property.getHost().addRejectedBooking(this);
    }

    public boolean dateOverlaps(LocalDate arrival, LocalDate departure) {
        return !arrival.isAfter(this.departureDate) && !departure.isBefore(this.arrivalDate);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        return ((Booking) o).getIdentifier().equals(getIdentifier());
    }

    @Override
    public boolean rejectOrCancel(Booking b) {
        if (this.departureDate.isBefore(b.getArrivalDate())) {
            if (this.state.equals(BookingState.REQUESTED)) {
                this.state = BookingState.REJECTED;
                property.getHost().addRejectedBooking(this);
                return true;
            }
            if (this.state.equals(BookingState.CONFIRMED)) {
                this.state = BookingState.CANCELLED;
                return true;
            }
        }
        return false;
    }

    @Override
    public Host getHost() {
        return this.property.getHost();
    }
}
