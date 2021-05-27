package exceptions.booking;

public class GuestHasNoBookingsException extends Exception {
    private static final String MESSAGE = "Guest %s has no bookings.";

    public GuestHasNoBookingsException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
