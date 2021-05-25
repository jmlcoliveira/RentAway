package exceptions;

public class UserHasNoBookingsException extends Exception {
    private static final String MESSAGE = "User %s has no bookings.";

    public UserHasNoBookingsException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
