package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class PropertyHasNoStaysException extends Exception {

    private static final String MESSAGE = "Property %s does not have any stays.";

    public PropertyHasNoStaysException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
