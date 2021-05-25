package exceptions;

public class UserNotHostOfBookingException extends Exception{
    private static final String MESSAGE = "User %s is not the host of booking %s.";

    public UserNotHostOfBookingException(String userID, String bookingID) {
        super(String.format(MESSAGE, userID, bookingID));
    }
}
