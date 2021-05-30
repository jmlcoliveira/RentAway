package exceptions.user;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class NoUsersRegisteredException extends Exception{

    private static final String MESSAGE = "No users registered.";

    public NoUsersRegisteredException() {super(MESSAGE);}
}
