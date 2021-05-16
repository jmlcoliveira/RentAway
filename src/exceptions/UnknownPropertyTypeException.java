package exceptions;

public class UnknownPropertyTypeException extends Exception{
    private static final String MESSAGE = "Unknown rental property type.";

    public UnknownPropertyTypeException(){
        super(MESSAGE);
    }
}
