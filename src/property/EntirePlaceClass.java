package property;

import booking.Booking;
import users.Host;

import java.time.Duration;

/**
 * Type of rental property that has a number of rooms and a variety of house types
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class EntirePlaceClass extends PropertyClass implements EntirePlace {

    /**
     * Type of this property
     */
    private final PropertyType propertyType = PropertyType.ENTIRE_PLACE;

    /**
     * Minimum duration of a booking to be auto confirmed
     */
    private final int DAYS_TO_AUTO_CONFIRM_BOOKING = 8;

    /**
     * Number of Rooms in the property
     */
    private final int numOfRooms;

    /**
     * Type of Entire Place Property
     */
    private final PlaceType type;

    /**
     * Constructor method
     *
     * @param identifier ID of the property
     * @param host       Host of the property
     * @param location   Location of the property
     * @param capacity   Capacity of the property
     * @param price      Price of the property
     * @param numOfRooms Number of rooms of the property
     * @param type       Type place
     */
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
        long duration = Duration.between(booking.getArrivalDate().atStartOfDay(), booking.getDepartureDate().atStartOfDay()).toDays();
        if (duration >= DAYS_TO_AUTO_CONFIRM_BOOKING && !bookingOverlaps(booking)) {
            booking.forceConfirm();
            booking.getGuest().addConfirmedBooking(booking);
            addConfirmedBooking(booking);
        }
        super.addBooking(booking);
    }
}
