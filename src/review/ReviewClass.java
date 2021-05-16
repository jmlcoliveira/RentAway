package review;

import booking.Booking;

public class ReviewClass implements Review{
    private final Booking booking;
    private String comment;
    private Rating rating;

    public ReviewClass(Booking booking, String comment, Rating rating){
        this.booking = booking;
        this.comment = comment;
        this.rating = rating;
    }
}
