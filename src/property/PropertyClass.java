package property;

import booking.Booking;
import booking.BookingState;
import review.Review;
import users.Host;
import java.util.List;

public abstract class PropertyClass implements Property {
    private final String identifier;
    private final String location;
    private Host host;
    private int guestsCapacity;
    private int price;
    private List<Booking> bookingList;
    private List<Review> reviewList;
    private final PropertyType TYPE;

    public PropertyClass(String identifier, String location, Host host, int guestsCapacity, int price,
                         PropertyType type){
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.guestsCapacity = guestsCapacity;
        this.price = price;
        this.TYPE = type;
    }

    public int getGuestsCapacity() {
        return guestsCapacity;
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

    public int getBookingCount() {
        return bookingList.size();
    }

    public int getReviewCount() { return reviewList.size(); }

    public PropertyType getType() {
        return TYPE;
    }

    @Override
    public int compareTo(Property p){
        return getIdentifier().compareTo(p.getIdentifier());
    }

    @Override
    public double getTotalPayment() {
        double sumPay = 0;

        for(Booking booking : bookingList) {
            BookingState nextState = booking.getState();

            if(nextState.equals(BookingState.CONFIRMED) || nextState.equals(BookingState.PAID))
                sumPay += booking.getPrice();
        }
        return sumPay;
    }

    public void addBooking(Booking b){
        bookingList.add(b);
    }

    public boolean hasBooking(){

    }

    public List<Booking> getBookings(){
        return bookingList;
    }


    @Override
    public int getPaidBookingCount() {
        return 0;
    }

    @Override
    public double getAverageRating() {
        return 0;
    }
}


