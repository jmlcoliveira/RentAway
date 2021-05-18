package users;

import exceptions.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public class HostClass extends UserClassAbs implements Host {
    private List<Property> properties;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>();
    }

    public int numOfProperties() {
        return properties.size();
    }

    public int totalPropertiesPaid() {
        return 0;
    }

    public double averageRating() {
        return 0;
    }

    public Iterator<Property> propertyIt() throws NoPropertiesRegisteredException {
        if(properties.size() == 0) throw new NoPropertiesRegisteredException(getName());
        return properties.iterator();
    }

    public double getTotalPayment() {
        double sumPay = 0;

        for (Property property : properties) {
            sumPay += property.getTotalPayment();
        }
    }
}
