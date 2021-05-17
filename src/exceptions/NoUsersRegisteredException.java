package exceptions;

public class NoUsersRegisteredException extends Exception{

    private static final String MESSAGE = "No users registered.";

    public NoUsersRegisteredException() {super(MESSAGE);}
}
