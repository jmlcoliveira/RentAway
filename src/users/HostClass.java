package users;

import booking.Booking;
import exceptions.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public class HostClass extends UserClassAbs implements Host {
    //todo
    private final List<Property> properties;
    private final List<Booking> rejectedBookings;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>();
        rejectedBookings = new ArrayList<>();
    }

    public int numOfProperties() {
        return properties.size();
    }

    public Iterator<Property> propertyIt() throws NoPropertiesRegisteredException {
        if(properties.size() == 0) throw new NoPropertiesRegisteredException(getName());
        return properties.iterator();
    }

    public Iterator<Booking> iteratorRejectedBookings(){
        return rejectedBookings.iterator();
    }
}
