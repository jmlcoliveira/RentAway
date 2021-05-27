package exceptions.booking;

public class InvalidBookingDatesException extends Exception {
    private static final String MESSAGE = "Invalid booking dates.";

    public InvalidBookingDatesException() {
        super(MESSAGE);
    }
}
