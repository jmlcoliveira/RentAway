package booking.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class BookingDoesNotExistException extends Exception {

    private static final String MESSAGE = "Booking %s does not exist.";

    public BookingDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
