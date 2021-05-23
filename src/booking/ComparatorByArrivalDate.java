package booking;

import java.util.Comparator;

public class ComparatorByArrivalDate implements Comparator<Booking> {

    @Override
    public int compare(Booking b1, Booking b2) {
        if (b1.getArrivalDate().isAfter(b2.getArrivalDate())) return -1;
        if (b1.getArrivalDate().isBefore(b2.getArrivalDate())) return 1;
        return 0;
    }

}
