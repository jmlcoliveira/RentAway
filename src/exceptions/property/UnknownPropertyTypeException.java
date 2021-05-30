package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class UnknownPropertyTypeException extends Exception{
    private static final String MESSAGE = "Unknown rental property type.";

    public UnknownPropertyTypeException(){
        super(MESSAGE);
    }
}
