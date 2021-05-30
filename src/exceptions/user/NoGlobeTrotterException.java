package exceptions.user;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class NoGlobeTrotterException extends Exception {

    private static final String MESSAGE = "No globe trotter.";

    public NoGlobeTrotterException() {
        super(MESSAGE);
    }
}
