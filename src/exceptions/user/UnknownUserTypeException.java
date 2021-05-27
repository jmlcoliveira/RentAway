package exceptions.user;

public class UnknownUserTypeException extends Exception{
    private static final String MESSAGE = "Unknown user type";

    public UnknownUserTypeException(){
        super(MESSAGE);
    }
}
