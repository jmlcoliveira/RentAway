package exceptions.property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class NumGuestsExceedsCapacityException extends Exception{

    private static final String MESSAGE = "Property %s has a capacity limit of %d guests.";

    public NumGuestsExceedsCapacityException(String id, int capacity) {
        super(String.format(MESSAGE, id, capacity));
    }
}
