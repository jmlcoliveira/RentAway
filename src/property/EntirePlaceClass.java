package property;

import users.Host;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class EntirePlaceClass extends PropertyClass implements EntirePlace {
    private final PropertyType propertyType = PropertyType.ENTIRE_PLACE;

    private final int numOfRooms;
    private final PlaceType type;

    public EntirePlaceClass(String identifier, Host host, String location, int capacity,
                            int price, int numOfRooms, PlaceType type) {
        super(identifier, location, host, capacity, price);
        this.numOfRooms = numOfRooms;
        this.type = type;
    }

    public PropertyType getType() {
        return propertyType;
    }
}
