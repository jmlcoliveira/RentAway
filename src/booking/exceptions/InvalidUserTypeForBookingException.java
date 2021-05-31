package booking.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class InvalidUserTypeForBookingException extends Exception{

    private static final String MESSAGE = "User %s is not the %s of booking %s.";

    public InvalidUserTypeForBookingException(String userID, String userType ,String bookingID) {
        super(String.format(MESSAGE, userID, userType, bookingID));
    }
}
