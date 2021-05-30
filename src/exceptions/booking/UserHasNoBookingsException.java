package exceptions.booking;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class UserHasNoBookingsException extends Exception {

    private static final String MESSAGE = "User %s has no bookings.";

    public UserHasNoBookingsException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
