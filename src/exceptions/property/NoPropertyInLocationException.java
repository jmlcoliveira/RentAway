package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class NoPropertyInLocationException extends Exception {

    private static final String MESSAGE = "No property was found in %s.";

    public NoPropertyInLocationException (String location){
        super(String.format(MESSAGE, location));
    }
}
