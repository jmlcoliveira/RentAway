package property;

import users.Host;

public class EntirePlaceClass extends PropertyClass implements EntirePlace{
    private int roomsNumber;
    private PropertyType type;

    public EntirePlaceClass(String identifier, String location, Host host, int capacity, int price) {
        super(identifier, location, host, capacity, price);
    }
}
