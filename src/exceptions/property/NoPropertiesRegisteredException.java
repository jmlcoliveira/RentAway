package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class NoPropertiesRegisteredException extends Exception{

    private static final String MESSAGE = "Host %s has no registered properties.";

    public NoPropertiesRegisteredException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
