package exceptions;

public class CannotConfirmBookingException extends Exception {
    public static String MESSAGE = "Cannot confirm booking %s that is in state %s";

    public CannotConfirmBookingException(String identifier, String state){
        super(String.format(MESSAGE, identifier, state));
    }
}
