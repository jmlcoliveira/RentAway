package users;

import exceptions.NoPropertiesRegisteredException;
import property.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HostClass extends UserClassAbs implements Host {
    private List<Property> properties;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
        properties = new ArrayList<>();
    }

    public int numOfProperties() {
        return properties.size();
    }

    public Iterator<Property> propertyIt() throws NoPropertiesRegisteredException {
        return properties.iterator();
    }
}
