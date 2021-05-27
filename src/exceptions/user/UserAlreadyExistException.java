package exceptions.user;

public class UserAlreadyExistException extends Exception {
    private static final String MESSAGE = "User %s already exists.";

    public UserAlreadyExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
