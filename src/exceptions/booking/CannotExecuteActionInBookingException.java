package exceptions.booking;

public class CannotExecuteActionInBookingException extends Exception{
    private static final String MESSAGE = "Cannot %s booking %s that is in state %s.";

    public CannotExecuteActionInBookingException(String action, String identifier, String state){
        super(String.format(MESSAGE, action, identifier, state));
    }
}
