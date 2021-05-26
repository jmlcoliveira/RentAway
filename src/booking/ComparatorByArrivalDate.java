package booking;

import java.util.Comparator;

public class ComparatorByArrivalDate implements Comparator<Booking> {

    @Override
    public int compare(Booking b1, Booking b2) {
        if (b2.getArrivalDate().isBefore(b1.getArrivalDate())) return -1;
        if (b2.getArrivalDate().isAfter(b1.getArrivalDate())) return 1;
        return 0;

        /*
        *         LocalDate now = LocalDate.now();
        LocalDate b1Arrival = b1.getArrivalDate();
        LocalDate b2Arrival = b2.getArrivalDate();
        long differenceNowB1 =
                Duration.between(now.atStartOfDay(), b1Arrival.atStartOfDay()).toDays();
        long differenceNowB2 =
                Duration.between(now.atStartOfDay(), b2Arrival.atStartOfDay()).toDays();
        return (int) (differenceNowB1-differenceNowB2);
        * */
    }

}
