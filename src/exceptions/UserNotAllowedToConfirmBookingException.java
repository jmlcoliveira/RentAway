package exceptions;

public class UserNotAllowedToConfirmBookingException extends Exception {
    private static final String MESSAGE = "User %s is not allowed to confirm the booking.";

    public UserNotAllowedToConfirmBookingException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
