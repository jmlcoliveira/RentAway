package property;

import booking.Booking;
import users.Host;

import java.time.temporal.ChronoUnit;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class EntirePlaceClass extends PropertyClass implements EntirePlace {
    private static final PropertyType propertyType = PropertyType.ENTIRE_PLACE;
    private static final int DAYS_TO_AUTO_CONFIRM_BOOKING = 8;

    private final int numOfRooms;
    private final PlaceType type;

    public EntirePlaceClass(String identifier, Host host, String location, int capacity,
                            double price, int numOfRooms, PlaceType type) {
        super(identifier, location, host, capacity, price);
        this.numOfRooms = numOfRooms;
        this.type = type;
    }

    public final PropertyType getType() {
        return propertyType;
    }

    /**
     * Validate if booking should be automatically confirmed
     *
     * @param booking the booking being added
     */
    @Override
    public final void addBooking(Booking booking) {
        long duration = ChronoUnit.DAYS.between(booking.getArrivalDate(), booking.getDepartureDate());
        if (duration >= DAYS_TO_AUTO_CONFIRM_BOOKING && !bookingOverlaps(booking)) {
            booking.forceConfirm();
            booking.getGuest().addConfirmedBooking(booking);
            addConfirmedBooking(booking);
        }
        super.addBooking(booking);
    }
}
