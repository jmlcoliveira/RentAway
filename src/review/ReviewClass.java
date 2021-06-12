package review;

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
     * Constructor method
     *
     * @param comment Comment given
     * @param rating  Rating given
     */
    public ReviewClass(String comment, Rating rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public int getRating() {
        return rating.getRating();
    }
}
