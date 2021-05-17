package exceptions;

public class UserNotAllowedToReview extends Exception {
    private static final String MESSAGE = "User %s is not allowed to review the booking";

     public UserNotAllowedToReview(String identifier){
            super(String.format(MESSAGE, identifier));
     }
}
