package users;
import exceptions.NoPropertiesRegisteredException;
import property.Property;
import java.util.*;

public interface Host extends User {

    int numOfProperties();

    Iterator<Property> propertyIt() throws NoPropertiesRegisteredException;
}
