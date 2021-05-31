package booking.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class BookingAlreadyReviewedException extends Exception {
    private static final String MESSAGE = "Booking %s was already reviewed.";

    public BookingAlreadyReviewedException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
