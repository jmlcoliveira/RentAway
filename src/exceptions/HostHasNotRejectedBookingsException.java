package exceptions;

public class HostHasNotRejectedBookingsException extends Exception {
    private static final String MESSAGE = "Host %s has not rejected any bookings";

    public HostHasNotRejectedBookingsException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
