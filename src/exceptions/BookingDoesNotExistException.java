package exceptions;

public class BookingDoesNotExistException extends Exception {
    private static final String MESSAGE = "Booking %s does not exists.";

    public BookingDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
