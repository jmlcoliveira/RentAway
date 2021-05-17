package users;
import exceptions.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public interface Host extends User {

    int numOfProperties();

    int numOfBookings();

    int totalPropertiesPaid();

    double averageRating();

    double getTotalPayment();

    Iterator<Property> propertyIt() throws NoPropertiesRegisteredException;
}
