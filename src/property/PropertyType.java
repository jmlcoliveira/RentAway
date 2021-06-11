package property;

import java.util.Locale;

/**
 * Enum with every type of property
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum PropertyType {
    ENTIRE_PLACE, PRIVATE_ROOM, UNKNOWN;

    @Override
    public String toString() {
        return this.name().replace("_", " ").toLowerCase(Locale.ROOT);
    }
}
