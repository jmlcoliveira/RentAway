package users.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class UserAlreadyExistException extends Exception {
    private static final String MESSAGE = "User %s already exists.";

    public UserAlreadyExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
