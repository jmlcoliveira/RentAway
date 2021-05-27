package exceptions.booking;

public class InvalidUserTypeForBookingException extends Exception{
    private static final String MESSAGE = "User %s is not the %s of booking %s.";

    public InvalidUserTypeForBookingException(String userID, String userType ,String bookingID) {
        super(String.format(MESSAGE, userID, userType, bookingID));
    }
}
