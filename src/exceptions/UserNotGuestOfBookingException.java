package exceptions;

public class UserNotGuestOfBookingException extends Exception{
    private static final String MESSAGE = "User %s is not allowed to pay the booking.";

    public UserNotGuestOfBookingException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
