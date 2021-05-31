package property.exceptions;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class PropertyDoesNotExistException extends Exception {

    private static final String MESSAGE = "Property %s does not exist.";

    public PropertyDoesNotExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
