package exceptions;

public class NoGlobalTrotterException extends Exception {

    private static final String MESSAGE = "No globe trotter.";

    public NoGlobalTrotterException() {
        super(MESSAGE);
    }
}
