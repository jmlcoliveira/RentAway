package property;

import users.Host;

import java.util.List;

public class PrivateRoomClass extends PropertyClass implements PrivateRoom{
    private List<String> amenities;

    public PrivateRoomClass(String identifier, String location, Host host, int capacity, int price) {
        super(identifier, location, host, capacity, price);
    }
}
