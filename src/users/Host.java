package users;
import booking.Booking;
import exceptions.property.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public interface Host extends User {

    int numOfProperties();

    Iterator<Property> propertyIt() throws NoPropertiesRegisteredException;

    int getBookingsTotal();

    int getRejectedBookings();

    void addBooking(Booking booking);

    void addRejectedBooking(Booking booking);

    void addProperty(Property property);

    Iterator<Booking> iteratorRejectedBookings();
}
