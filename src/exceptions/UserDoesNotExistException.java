package exceptions;

public class UserDoesNotExistException extends Exception{

    private static final String MESSAGE = "User %s does not exists.";

    public UserDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
