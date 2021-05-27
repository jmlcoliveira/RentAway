package exceptions.booking;

public class BookingDoesNotExistException extends Exception {
    private static final String MESSAGE = "Booking %s does not exist.";

    public BookingDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
