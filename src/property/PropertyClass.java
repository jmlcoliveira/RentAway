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

    public PropertyClass(String identifier, String location, Host host, int capacity, int price){
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public int bookingCount() {
        return bookingList.size();
    }

    public int reviewCount() {
        return reviewList.size();
    }
}


