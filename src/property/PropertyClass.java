package property;

import users.Host;

public abstract class PropertyClass implements Property {
    private final String identifier;
    private final String location;
    private Host host;
    private int capacity;
    private int price;

    public PropertyClass(String identifier, String location, Host host, int capacity, int price){
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.capacity = capacity;
        this.price = price;
    }
}
