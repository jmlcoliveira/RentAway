package exceptions;

public class UserNotAllowedToReviewException extends Exception {
    private static final String MESSAGE = "User %s is not the guest of booking %s";

     public UserNotAllowedToReviewException(String identifier, String bookingID){
            super(String.format(MESSAGE, identifier, bookingID));
     }
}
