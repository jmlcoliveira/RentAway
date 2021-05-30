package exceptions.booking;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class InvalidBookingDatesException extends Exception {

    private static final String MESSAGE = "Invalid booking dates.";

    public InvalidBookingDatesException() {
        super(MESSAGE);
    }
}
