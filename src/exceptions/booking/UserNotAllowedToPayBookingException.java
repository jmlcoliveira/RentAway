package exceptions.booking;

public class UserNotAllowedToPayBookingException extends Exception{
    private static final String MESSAGE = "User %s is not allowed to pay the booking.";

    public UserNotAllowedToPayBookingException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
