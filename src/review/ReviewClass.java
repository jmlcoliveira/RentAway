package review;

import booking.Booking;

/**
 * A review a guest makes on a property with a comment and a rating
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class ReviewClass implements Review {

    /**
     * Comment from this review
     */
    private final String comment;

    /**
     * Rating given to the property 1-5
     */
    private final Rating rating;

    /**
     * Booking of the review
     */
    private final Booking booking;

    /**
     * Constructor method
     *
     * @param comment Comment given
     * @param rating  Rating given
     * @param booking Booking of the review
     */
    public ReviewClass(String comment, Rating rating, Booking booking) {
        this.comment = comment;
        this.rating = rating;
        this.booking = booking;
    }

    public int getRating() {
        return rating.getRating();
    }
}
