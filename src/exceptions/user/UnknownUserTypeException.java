package exceptions.user;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class UnknownUserTypeException extends Exception{
    private static final String MESSAGE = "Unknown user type";

    public UnknownUserTypeException(){
        super(MESSAGE);
    }
}
