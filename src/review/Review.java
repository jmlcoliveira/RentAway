package review;

/**
 * A review a guest makes of a property with a comment and a rating
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface Review {

    /**
     * Gets the rating of the review
     *
     * @return rating from 1 to 5
     */
    int getRating();
}
