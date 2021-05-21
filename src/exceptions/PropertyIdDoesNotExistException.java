package exceptions;

public class PropertyIdDoesNotExistException extends Exception {
    private static final String MESSAGE = "Property %s does not exist.";

    public PropertyIdDoesNotExistException(String propertyID){
        super(String.format(MESSAGE, propertyID));
    }
}
