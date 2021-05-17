package exceptions;

public class NumGuestsExceedsCapacityException extends Exception{

    private static final String MESSAGE = "Property %s has a capacity limit of %d guests";

    public NumGuestsExceedsCapacityException(String id, int capacity) {
        super(String.format(MESSAGE, id, capacity));
    }
}
