package review;

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
