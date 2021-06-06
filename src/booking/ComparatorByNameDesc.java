package booking;

import java.util.Comparator;

/**
 * Comparator to sort bookings by inverse insertion order
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class ComparatorByNameDesc implements Comparator<Booking> {

    @Override
    public int compare(Booking b1, Booking b2) {
        return b2.getIdentifier().compareTo(b1.getIdentifier());
    }
}
