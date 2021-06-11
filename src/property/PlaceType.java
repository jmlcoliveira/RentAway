package property;

import java.util.Locale;

/**
 * Enum with every type of entire place
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum PlaceType {
    APARTMENT, HOUSE, CABIN;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
