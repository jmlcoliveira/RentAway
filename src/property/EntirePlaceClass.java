package property;

import users.Host;

public class EntirePlaceClass extends PropertyClass implements EntirePlace{
    private int numOfRooms;
    private PlaceType type;

    public EntirePlaceClass(String identifier, Host host, String location, int capacity,
                            int price, int numOfRooms, PlaceType type) {
        super(identifier, location, host, capacity, price, PropertyType.ENTIRE_PLACE);
        this.numOfRooms = numOfRooms;
    }

    public PropertyType type() {
        return PropertyType.ENTIRE_PLACE;
    }
}
