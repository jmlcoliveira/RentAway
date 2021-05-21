package property;

import users.Host;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrivateRoomClass extends PropertyClass implements PrivateRoom{
    private List<String> amenities;

    public PrivateRoomClass(String identifier, Host host, String location, int capacity,
                            int price, int amenities) {
        super(identifier, location, host, capacity, price, PropertyType.PRIVATE_ROOM);
        this.amenities = new ArrayList<>(amenities);
    }

    public PropertyType type() {
        return PropertyType.PRIVATE_ROOM;
    }

    @Override
    public void addAmenity(String amenity) {
        amenities.add(amenity);
    }
}
