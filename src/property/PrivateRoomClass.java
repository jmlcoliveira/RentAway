package property;

import users.Host;

import java.util.ArrayList;
import java.util.List;

/**
 * Type of rental property, with a number of amenities the guests have access to
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class PrivateRoomClass extends PropertyClass implements PrivateRoom {

    /**
     * Type of this property
     */
    private static final PropertyType propertyType = PropertyType.PRIVATE_ROOM;

    /**
     * List of amenities a guest has access
     */
    private final List<String> amenities;

    /**
     * Constructor method
     *
     * @param identifier ID of the property
     * @param host       Host of the property
     * @param location   Location of the property
     * @param capacity   Capacity of the property
     * @param price      Price of the property
     * @param amenities  Number of amenities
     */
    public PrivateRoomClass(String identifier, Host host, String location, int capacity,
                            double price, int amenities) {
        super(identifier, location, host, capacity, price);
        this.amenities = new ArrayList<>(amenities);
    }

    public final PropertyType getType() {
        return propertyType;
    }

    public final void addAmenity(String amenity) {
        amenities.add(amenity);
    }
}
