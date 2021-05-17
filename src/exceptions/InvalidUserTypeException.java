package exceptions;

public class InvalidUserTypeException extends Exception {

    private static final String MESSAGE = "User %s is not a %s user.";

    public InvalidUserTypeException(String identifier, String type){
        super(String.format(MESSAGE, identifier, type));
    }
}
