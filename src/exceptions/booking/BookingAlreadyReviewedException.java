package exceptions.booking;

public class BookingAlreadyReviewedException extends Exception {
    private static final String MESSAGE = "Booking %s was already reviewed.";

    public BookingAlreadyReviewedException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
