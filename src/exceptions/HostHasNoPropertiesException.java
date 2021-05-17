package exceptions;

public class HostHasNoPropertiesException extends Exception {
    private static final String MESSAGE = "Host %s has no registered properties.";

    public HostHasNoPropertiesException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
