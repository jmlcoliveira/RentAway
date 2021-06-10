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

    private final int rating;

    Rating(int rating){
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
