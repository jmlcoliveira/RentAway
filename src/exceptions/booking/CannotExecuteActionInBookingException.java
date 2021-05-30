package exceptions.booking;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class CannotExecuteActionInBookingException extends Exception{

    private static final String MESSAGE = "Cannot %s booking %s that is in state %s.";

    public CannotExecuteActionInBookingException(String action, String identifier, String state){
        super(String.format(MESSAGE, action, identifier, state));
    }
}
