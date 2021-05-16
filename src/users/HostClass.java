package users;

import property.Property;

import java.util.List;

public class HostClass extends UserClassAbs implements Host {
    private List<Property> properties;

    public HostClass(String identifier, String name, String nationality, String email) {
        super(identifier, name, nationality, email);
    }
}
