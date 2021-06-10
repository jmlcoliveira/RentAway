package booking;

import java.util.Comparator;

/**
 * Comparator to sort bookings by inverse insertion order
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class ComparatorByInsertion implements Comparator<Booking> {

    private final boolean inverseInsertionOrder;

    public ComparatorByInsertion(boolean inverseInsertionOrder) {
        this.inverseInsertionOrder = inverseInsertionOrder;
    }

    @Override
    public int compare(Booking b1, Booking b2) {
        if (inverseInsertionOrder)
            return b2.getBookingNum() - b1.getBookingNum();
        return b1.getBookingNum() - b2.getBookingNum();
    }
}
