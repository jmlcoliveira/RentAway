package exceptions.property;

public class NoPropertiesRegisteredException extends Exception{

    private static final String MESSAGE = "Host %s has no registered properties.";

    public NoPropertiesRegisteredException(String identifier){
        super(String.format(MESSAGE, identifier));
    }
}
