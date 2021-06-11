package booking;

import java.util.Locale;

/**
 * Enum with the possible booking states
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum BookingState {
    REQUESTED,
    CONFIRMED,
    REJECTED,
    PAID,
    REVIEWED,
    CANCELLED;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
