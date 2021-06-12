package property;

import java.util.Locale;

/**
 * Enum with every type of entire place
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum PlaceType {
    APARTMENT, HOUSE, CABIN;

    /**
     * Returns the enum value in lower case
     *
     * @return the enum value in lower case
     */
    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
