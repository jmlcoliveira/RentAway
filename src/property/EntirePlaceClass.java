package property;

import users.Host;

public class EntirePlaceClass extends PropertyClass implements EntirePlace{
    private int roomsNumber;
    private PlaceType type;

    public EntirePlaceClass(String identifier, String location, Host host, int capacity, int price) {
        super(identifier, location, host, capacity, price);
    }

    public PropertyType type() {
        return PropertyType.ENTIRE_PLACE;
    }
}
