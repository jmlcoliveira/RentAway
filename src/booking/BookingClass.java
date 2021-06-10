package booking;

import booking.exceptions.BookingAlreadyReviewedException;
import booking.exceptions.CannotExecuteActionInBookingException;
import commands.Command;
import property.Property;
import review.Rating;
import review.Review;
import review.ReviewClass;
import users.Guest;
import users.Host;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class BookingClass implements Booking {
    private static int BOOKING_COUNTER = 0;
    private final int bookingNum;
    private final String identifier;
    private final Guest guest;
    private final Property property;
    private final int numberOfGuests;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private Review review;
    private BookingState state;

    public BookingClass(String bookingID, Guest guest, Property property, int numberOfGuests,
                        LocalDate arrivalDate, LocalDate departureDate, boolean isTemp) {
        this.identifier = bookingID;
        this.guest = guest;
        this.property = property;
        this.numberOfGuests = numberOfGuests;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        if (isTemp)
            bookingNum = -1;
        else
            bookingNum = BOOKING_COUNTER++;
        state = BookingState.REQUESTED;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public int getBookingNum() {
        return bookingNum;
    }

    public final Guest getGuest() {
        return guest;
    }

    public final int getNumberOfGuests() {
        return numberOfGuests;
    }

    public final double getPaidAmount() {
        return isPaid() ? property.getPrice() * ChronoUnit.DAYS.between(arrivalDate, departureDate) : 0;
    }

    public final BookingState getState() {
        return state;
    }

    public final Property getProperty() {
        return property;
    }

    public final LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public final LocalDate getDepartureDate() {
        return departureDate;
    }

    public final boolean isPaid() {
        return (state == BookingState.PAID || state == BookingState.REVIEWED);
    }

    public final void review(String comment, String classification) throws BookingAlreadyReviewedException, CannotExecuteActionInBookingException {
        if (!isPaid())
            throw new CannotExecuteActionInBookingException(Command.REVIEW.name().toLowerCase(), identifier, state.name().toLowerCase());
        if (state == BookingState.REVIEWED) throw new BookingAlreadyReviewedException(identifier);

        review = new ReviewClass(comment, Rating.valueOf(classification.toUpperCase()));
        property.addReview(review);
        state = BookingState.REVIEWED;
    }

    public final void confirm() throws CannotExecuteActionInBookingException {
        if (!this.state.equals(BookingState.REQUESTED))
            throw new CannotExecuteActionInBookingException(
                    Command.CONFIRM.name().toLowerCase(),
                    identifier,
                    state.name().toLowerCase());
        this.state = BookingState.CONFIRMED;
    }

    public final void forceConfirm() {
        this.state = BookingState.CONFIRMED;
    }

    public final void pay() throws CannotExecuteActionInBookingException {
        if (!this.state.equals(BookingState.CONFIRMED))
            throw new CannotExecuteActionInBookingException(Command.PAY.name().toLowerCase(), identifier,
                    state.name().toLowerCase());
        this.state = BookingState.PAID;
        guest.addPaidBooking(this);
    }

    public final void reject() throws CannotExecuteActionInBookingException {
        if (state != BookingState.REQUESTED)
            throw new CannotExecuteActionInBookingException(Command.REJECT.name().toLowerCase(), identifier, state.name().toLowerCase());
        state = BookingState.REJECTED;
        property.getHost().addRejectedBooking(this);
    }

    public final boolean dateOverlaps(LocalDate arrival, LocalDate departure) {
        return !arrival.isAfter(this.departureDate) && !departure.isBefore(this.arrivalDate);
    }

    public final boolean rejectedOrCanceled(Booking b) {
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

    public final Host getHost() {
        return property.getHost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        return ((Booking) o).getIdentifier().equals(getIdentifier());
    }
}
