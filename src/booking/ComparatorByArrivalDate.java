package booking;

import java.util.Comparator;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class ComparatorByArrivalDate implements Comparator<Booking> {

    @Override
    public int compare(Booking b1, Booking b2) {
        //return b2.getIdentifier().compareTo(b1.getIdentifier());
        if (b2.getArrivalDate().isBefore(b1.getArrivalDate())) return -1;
        if (b2.getArrivalDate().isAfter(b1.getArrivalDate())) return 1;
        return 0;
    }

}
