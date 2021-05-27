package exceptions.property;

public class PropertyAlreadyExistException extends Exception {
    private static final String MESSAGE = "Property %s already exists.";

    public PropertyAlreadyExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
