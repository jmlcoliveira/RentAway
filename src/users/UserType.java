package users;

import java.util.Locale;

/**
 * Enum with every type of user
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum UserType {
    GUEST,
    HOST,
    UNKNOWN;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
