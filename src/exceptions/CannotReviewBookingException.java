package exceptions;

public class CannotReviewBookingException extends Exception {
    private static final String MESSAGE = "Cannot review booking %s that is in state %s";

    public CannotReviewBookingException(String identifier, String state){
        super(String.format(MESSAGE, identifier, state));
    }
}
