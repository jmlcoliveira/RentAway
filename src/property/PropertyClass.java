package property;

import booking.Booking;
import review.Review;
import users.Host;
import java.util.List;

public abstract class PropertyClass implements Property {
    private final String identifier;
    private final String location;
    private Host host;
    private int capacity;
    private int price;
    private List<Booking> bookingList;
    private List<Review> reviewList;
    private final PropertyType TYPE;

    public PropertyClass(String identifier, String location, Host host, int capacity, int price,
                         PropertyType type){
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.capacity = capacity;
        this.price = price;
        this.TYPE = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public int bookingCount() {
        return bookingList.size();
    }

    public int reviewCount() {
        return reviewList.size();
    }

    public PropertyType getType() {
        return TYPE;
    }

    @Override
    public int compareTo(Property p){
        return getIdentifier().compareTo(p.getIdentifier());
    }
}


