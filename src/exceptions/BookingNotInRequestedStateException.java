package exceptions;

public class BookingNotInRequestedStateException extends Exception {
    public static String MESSAGE = "Cannot review booking %s that is in state %s";

    public BookingNotInRequestedStateException(String identifier, String state){
        super(String.format(MESSAGE, identifier, state));
    }
}
