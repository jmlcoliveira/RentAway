package property;

public interface Property {

    PropertyType type();

    String getIdentifier();

    String getLocation();

    int getCapacity();

    int getPrice();

    int bookingCount();

    int reviewCount();
}
