package users.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class InvalidUserTypeException extends Exception {

    private static final String MESSAGE = "User %s is not a %s user.";

    public InvalidUserTypeException(String identifier, String type){
        super(String.format(MESSAGE, identifier, type));
    }
}
