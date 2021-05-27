package property;

import users.Host;

import java.util.ArrayList;
import java.util.List;

public class PrivateRoomClass extends PropertyClass implements PrivateRoom {
    private final PropertyType propertyType = PropertyType.PRIVATE_ROOM;

    private final List<String> amenities;

    public PrivateRoomClass(String identifier, Host host, String location, int capacity,
                            int price, int amenities) {
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
