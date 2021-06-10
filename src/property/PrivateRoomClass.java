package property;

import users.Host;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class PrivateRoomClass extends PropertyClass implements PrivateRoom {
    private static final PropertyType propertyType = PropertyType.PRIVATE_ROOM;

    private final List<String> amenities;

    public PrivateRoomClass(String identifier, Host host, String location, int capacity,
                            double price, int amenities) {
        super(identifier, location, host, capacity, price);
        this.amenities = new ArrayList<>(amenities);
    }

    public final PropertyType getType() {
        return propertyType;
    }

    @Override
    public final void addAmenity(String amenity) {
        amenities.add(amenity);
    }
}
