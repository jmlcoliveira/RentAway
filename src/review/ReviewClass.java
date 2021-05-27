package review;

public class ReviewClass implements Review {
    private final String comment;
    private final Rating rating;

    public ReviewClass(String comment, Rating rating) {
        this.comment = comment;
        this.rating = rating;
    }


    public int getRating() {
        return rating.getRating();
    }
}
