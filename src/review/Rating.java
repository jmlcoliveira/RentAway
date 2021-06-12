package review;

/**
 * Enum with every classification a review can have
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum Rating {
    TERRIBLE(1),
    POOR(2),
    AVERAGE(3),
    GOOD(4),
    EXCELLENT(5);

    /**
     * Value of each rating
     */
    private final int rating;

    Rating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the value of each rating
     *
     * @return the value of each rating
     */
    public int getRating() {
        return rating;
    }
}
