package exceptions.booking;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class GuestHasNoBookingsException extends Exception {

    private static final String MESSAGE = "Guest %s has no bookings.";

    public GuestHasNoBookingsException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
