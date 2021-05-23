package users;
import booking.Booking;
import exceptions.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public interface Host extends User {

    int numOfProperties();

    Iterator<Property> propertyIt() throws NoPropertiesRegisteredException;

    int getBookingsTotal();

    int getRejectedBookings();

    Iterator<Booking> iteratorRejectedBookings();
}
