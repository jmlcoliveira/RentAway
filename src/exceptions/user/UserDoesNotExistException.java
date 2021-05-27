package exceptions.user;

public class UserDoesNotExistException extends Exception{
    private static final String MESSAGE = "User %s does not exist.";

    public UserDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
