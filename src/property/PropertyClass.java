package property;

import booking.Booking;
import booking.BookingState;
import booking.ComparatorByArrivalDate;
import booking.exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.*;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public abstract class PropertyClass implements Property {

    private final String identifier;
    private final String location;
    private final Host host;
    private final int guestsCapacity;
    private final int price;
    private final List<Booking> bookingList;
    private final List<Review> reviewList;
    private final SortedSet<Booking> paidBookings;
    private final List<Booking> confirmedBookings;
    private final List<Booking> unpaidBookings;

    protected PropertyClass(String identifier, String location, Host host, int guestsCapacity, int price) {
        bookingList = new ArrayList<>();
        reviewList = new ArrayList<>();
        paidBookings = new TreeSet<>(new ComparatorByArrivalDate());
        confirmedBookings = new ArrayList<>();
        unpaidBookings = new LinkedList<>();
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.guestsCapacity = guestsCapacity;
        this.price = price;
    }

    public int getGuestsCapacity() {
        return guestsCapacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public double getPrice() {
        return price * 1.0;
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

    public boolean bookingOverlaps(Booking booking) {
        for (Booking b : bookingList)
            if (b.dateOverlaps(booking.getArrivalDate(), booking.getDepartureDate()))
                return true;
        return false;
    }

    public Booking getBooking(Booking b) {
        int i = bookingList.indexOf(b);
        return i != -1 ? bookingList.get(i) : null;
    }

    public void addReview(Review review) {
        reviewList.add(review);
    }

    public void addPaidBooking(Booking b) {
        paidBookings.add(b);
        unpaidBookings.remove(b);
        confirmedBookings.remove(b);
    }

    public void addConfirmedBooking(Booking booking) {
        confirmedBookings.add(booking);
    }

    public void addBooking(Booking booking) {
        bookingList.add(booking);
        unpaidBookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookingList;
    }

    public Iterator<Booking> iteratorPaidBookings() {
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
        if (reviewList.size() == 0) return rating;
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

        for (Booking b : unpaidBookings) {
            if (b.rejectOrCancel(booking))
                bookings.add(b);
        }
        return bookings.iterator();
    }

    public boolean isDateInvalid(LocalDate arrival, LocalDate departure) {
        for (Booking b : paidBookings) {
            if (!arrival.isAfter(b.getDepartureDate()))
                return true;
        }
        for (Booking b : confirmedBookings) {
            if (b.dateOverlaps(arrival, departure))
                return true;
        }
        return false;
    }

    public boolean hasStays() {
        return paidBookings.size() > 0;
    }

    public Iterator<Booking> confirmBooking(Booking booking) throws CannotExecuteActionInBookingException {
        booking.confirm();
        List<Booking> temp = new LinkedList<>();
        temp.add(booking);
        for (Booking b : unpaidBookings) {
            if (b.dateOverlaps(booking.getArrivalDate(), booking.getDepartureDate()) && (b.getState().equals(BookingState.REQUESTED))) {
                b.reject();
                temp.add(b);
            }
        }
        return temp.iterator();
    }
}


