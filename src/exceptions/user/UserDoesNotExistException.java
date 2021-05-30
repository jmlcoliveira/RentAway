package exceptions.user;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class UserDoesNotExistException extends Exception{
    private static final String MESSAGE = "User %s does not exist.";

    public UserDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
