package exceptions;

public class NoGlobalTrotterException extends Exception {

    private static final String MESSAGE = "No global trotter";

    public NoGlobalTrotterException() {
        super(MESSAGE);
    }
}
