package property;

import booking.Booking;
import booking.BookingState;
import booking.ComparatorByInsertion;
import booking.exceptions.CannotExecuteActionInBookingException;
import review.Review;
import users.Host;

import java.time.LocalDate;
import java.util.*;

/**
 * Abstract class of the different types of property
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public abstract class PropertyClass implements Property {

    /**
     * Property ID
     */
    private final String identifier;

    /**
     * Property Location
     */
    private final String location;

    /**
     * Host of the property
     */
    private final Host host;

    /**
     * Capacity of the property
     */
    private final int guestsCapacity;

    /**
     * Price per night of the property
     */
    private final double price;

    /**
     * Average Rating
     */
    private double averageRating;

    /**
     * List which contains every booking that was made at the property
     */
    private final List<Booking> bookingList;
    /**
     * List which contains every review that was made about the property
     */
    private final List<Review> reviewList;
    /**
     * Set which contains every paid booking, sorted by inverse insertion order
     */
    private final SortedSet<Booking> paidBookings;
    /**
     * List which contains every confirmed booking made at the property
     */
    private final List<Booking> confirmedBookings;
    /**
     * List which contains every unpaid booking made at the property
     */
    private final List<Booking> unpaidBookings;

    private LocalDate currentDate;

    protected PropertyClass(String identifier, String location, Host host, int guestsCapacity, double price) {
        bookingList = new ArrayList<>();
        reviewList = new ArrayList<>();
        paidBookings = new TreeSet<>(new ComparatorByInsertion(true));
        confirmedBookings = new LinkedList<>();
        unpaidBookings = new LinkedList<>();
        this.identifier = identifier;
        this.location = location;
        this.host = host;
        this.guestsCapacity = guestsCapacity;
        this.price = price;
        averageRating = 0;
    }

    public final int getGuestsCapacity() {
        return guestsCapacity;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final double getPrice() {
        return price;
    }

    public final String getLocation() {
        return location;
    }

    public final int getBookingCount() {
        return bookingList.size();
    }

    public final int getReviewCount() {
        return reviewList.size();
    }

    public final boolean bookingOverlaps(Booking booking) {
        for (Booking b : bookingList)
            if (b.dateOverlaps(booking.getArrivalDate(), booking.getDepartureDate()))
                return true;
        return false;
    }

    public final Booking getBooking(Booking tempBooking) {
        int i = bookingList.indexOf(tempBooking);
        return i != -1 ? bookingList.get(i) : null;
    }

    public final void addReview(Review review) {
        double totalRatings = averageRating * reviewList.size();
        reviewList.add(review);
        averageRating = (totalRatings + review.getRating()) / reviewList.size();
    }

    public final void addConfirmedBooking(Booking booking) {
        confirmedBookings.add(booking);
    }

    public void addBooking(Booking booking) {
        bookingList.add(booking);
        unpaidBookings.add(booking);
        host.addBooking(booking);
    }

    public final Iterator<Booking> iteratorPaidBookings() {
        return paidBookings.iterator();
    }

    public final Host getHost() {
        return host;
    }

    public final double getAverageRating() {
        return averageRating;
    }

    public final Iterator<Booking> pay(Booking booking) throws CannotExecuteActionInBookingException {
        booking.pay();
        addPaidBooking(booking);
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        for (Booking b : unpaidBookings) {
            if (b.rejectedOrCanceled(booking))
                bookings.add(b);
        }
        return bookings.iterator();
    }

    /**
     * Adds the booking to the paidBookings list, and removes it from other lists
     *
     * @param b the booking that was paid
     */
    private void addPaidBooking(Booking b) {
        paidBookings.add(b);
        unpaidBookings.remove(b);
        confirmedBookings.remove(b);
        currentDate = b.getDepartureDate();
    }

    public final boolean isDateInvalid(LocalDate arrival, LocalDate departure) {
        if (currentDate != null)
            if (!arrival.isAfter(currentDate))
                return true;

        for (Booking b : confirmedBookings) {
            if (b.dateOverlaps(arrival, departure))
                return true;
        }
        return false;
    }

    public final boolean hasStays() {
        return paidBookings.size() > 0;
    }

    public final Iterator<Booking> confirmBooking(Booking booking) throws CannotExecuteActionInBookingException {
        booking.confirm();
        List<Booking> temp = new ArrayList<>();
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


