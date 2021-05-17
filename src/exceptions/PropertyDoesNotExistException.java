package exceptions;

public class PropertyDoesNotExistException extends Exception {
    private static final String MESSAGE = "Property %s does not exist";

    public PropertyDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
