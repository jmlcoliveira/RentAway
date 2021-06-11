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

import java.time.Duration;
import java.time.LocalDate;

/**
 * Booking made in a property by a guest
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class BookingClass implements Booking {

    /**
     * Static variable containing the number of books created
     */
    private static int bookingCounter = 0;

    /**
     * Number of this booking
     */
    private final int bookingNum;

    /**
     * Booking ID
     */
    private final String identifier;

    /**
     * Guest of this booking
     */
    private final Guest guest;

    /**
     * Property of this booking
     */
    private final Property property;

    /**
     * Number of guests
     */
    private final int numberOfGuests;

    /**
     * Arrival Date of the booking
     */
    private final LocalDate arrivalDate;

    /**
     * Departure Date of the booking
     */
    private final LocalDate departureDate;

    /**
     * Review of the booking
     */
    private Review review;

    /**
     * Current state of the booking
     */
    private BookingState state;

    public BookingClass(String bookingID, Guest guest, Property property, int numberOfGuests,
                        LocalDate arrivalDate, LocalDate departureDate, boolean isTemp) {
        this.identifier = bookingID;
        this.guest = guest;
        this.property = property;
        this.numberOfGuests = numberOfGuests;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        if (isTemp) // validate if is a temporary booking only used for comparisons
            bookingNum = -1;
        else
            bookingNum = bookingCounter++;
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
        return isPaid() ? property.getPrice() * Duration.between(arrivalDate.atStartOfDay(), departureDate.atStartOfDay()).toDays() : 0;
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
            throw new CannotExecuteActionInBookingException(Command.REVIEW.toString(), identifier, state.name().toLowerCase());
        if (state == BookingState.REVIEWED) throw new BookingAlreadyReviewedException(identifier);

        review = new ReviewClass(comment, Rating.valueOf(classification.toUpperCase()));
        property.addReview(review);
        state = BookingState.REVIEWED;
    }

    public final void confirm() throws CannotExecuteActionInBookingException {
        if (!state.equals(BookingState.REQUESTED))
            throw new CannotExecuteActionInBookingException(
                    Command.CONFIRM.toString(),
                    identifier,
                    state.name().toLowerCase());
        state = BookingState.CONFIRMED;
    }

    public final void forceConfirm() {
        state = BookingState.CONFIRMED;
    }

    public final void pay() throws CannotExecuteActionInBookingException {
        if (!state.equals(BookingState.CONFIRMED))
            throw new CannotExecuteActionInBookingException(Command.PAY.toString(), identifier,
                    state.name().toLowerCase());
        state = BookingState.PAID;
        guest.addPaidBooking(this);
    }

    public final void reject() throws CannotExecuteActionInBookingException {
        if (state != BookingState.REQUESTED)
            throw new CannotExecuteActionInBookingException(Command.REJECT.toString(), identifier, state.name().toLowerCase());
        state = BookingState.REJECTED;
        property.getHost().addRejectedBooking(this);
    }

    public final boolean dateOverlaps(LocalDate arrival, LocalDate departure) {
        return !arrival.isAfter(departureDate) && !departure.isBefore(arrivalDate);
    }

    public final boolean rejectedOrCanceled(Booking b) {
        if (departureDate.isBefore(b.getArrivalDate())) {
            if (state.equals(BookingState.REQUESTED)) {
                state = BookingState.REJECTED;
                property.getHost().addRejectedBooking(this);
                return true;
            }
            if (state.equals(BookingState.CONFIRMED)) {
                state = BookingState.CANCELLED;
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
