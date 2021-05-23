package review;

import booking.Booking;

public class ReviewClass implements Review{
    private String comment;
    private Rating rating;

    public ReviewClass(String comment, Rating rating){
        this.comment = comment;
        this.rating = rating;
    }


    public int getRating() {
        return rating.getRating();
    }
}
