package property;

import booking.Booking;
import booking.BookingState;
import booking.ComparatorByArrivalDate;
import exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.*;

public abstract class PropertyClass implements Property {
    private final String identifier;
    private final String location;
    private final Host host;
    private final int guestsCapacity;
    private final int price;
    private final PropertyType TYPE;
    private final List<Booking> bookingList;
    private final List<Review> reviewList;
    private final SortedSet<Booking> paidBookings;

    public PropertyClass(String identifier, String location, Host host, int guestsCapacity, int price,
                         PropertyType type) {
        bookingList = new LinkedList<>();
        reviewList = new LinkedList<>();
        paidBookings = new TreeSet<>(new ComparatorByArrivalDate());
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

    public double getPrice() {
        return price * 1.00;
    }

    public String getLocation() {
        return location;
    }

    public int getBookingCount() {
        return bookingList.size();
    }

    public int getReviewCount() {
        return reviewList.size();
    }

    public PropertyType getType() {
        return TYPE;
    }

    /*@Override
    public double getTotalPayment() {
        double sumPay = 0.00;

        for (Booking booking : bookingList) {
            BookingState nextState = booking.getState();

            if (nextState.equals(BookingState.CONFIRMED) || nextState.equals(BookingState.PAID))
                sumPay += booking.getPrice();
        }
        return sumPay;
    }*/

    public boolean bookingOverlaps(Booking booking) {
        for (Booking b : bookingList)
            if (b.dateOverlaps(booking))
                return true;
        return false;
    }

    public LocalDate getPropertyLastPaidDepartureDate() {
        if (paidBookings.size() == 0) return null;
        LocalDate date = paidBookings.last().getDepartureDate();
        for (Booking b : paidBookings) {
            LocalDate d = b.getDepartureDate();
            if (d.isAfter(date))
                date = d;
        }
        return date;
    }

    public void addReview(Review review) {
        reviewList.add(review);
    }

    public void addPaidBooking(Booking b) {
        paidBookings.add(b);
    }

    public void addBooking(Booking booking) {
        bookingList.add(booking);
    }

    public List<Booking> getBookings() {
        return bookingList;
    }

    public Iterator<Booking> iteratorPaidBookings(){
        return paidBookings.iterator();
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public int getPaidBookingCount() {
        return paidBookings.size();
    }

    @Override
    public double getAverageRating() {
        double rating = 0.00;
        if(reviewList.size() == 0) return rating;
        for (Review r : reviewList)
            rating += r.getRating();
        return rating / reviewList.size();
    }

    @Override
    public int compareTo(Property p) {
        return getIdentifier().compareTo(p.getIdentifier());
    }

    @Override
    public Iterator<Booking> pay(Booking booking) throws CannotExecuteActionInBookingException {
        booking.pay();
        addPaidBooking(booking);
        booking.getGuest().addPaidBooking(booking);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        for (Booking b : bookingList) {
            if (b.rejectOrCancel(booking))
                bookings.add(b);
        }
        return bookings.iterator();
    }

    public Iterator<Booking> confirmBooking(Booking booking) throws CannotExecuteActionInBookingException {
        booking.confirm();
        List<Booking> temp = new LinkedList<>();
        temp.add(booking);
        Iterator<Booking> it = bookingList.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.dateOverlaps(booking) && (b.getState().equals(BookingState.REQUESTED))) {
                b.reject();
                temp.add(b);
            }
        }
        return temp.iterator();
    }
}


