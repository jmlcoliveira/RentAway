package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class PropertyAlreadyExistException extends Exception {

    private static final String MESSAGE = "Property %s already exists.";

    public PropertyAlreadyExistException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
