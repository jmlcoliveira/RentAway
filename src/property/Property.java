package property;

public interface Property {

    PropertyType type();

    String getIdentifier();

    String getLocation();

    int getCapacity();

    int getPrice();

    int getBookingCount();

    int getPaidBookingCount();

    int getReviewCount();

    double getAverageRating();

    double getTotalPayment();

    PropertyType getType();
}
