package property;

import java.util.Comparator;

public class ComparatorCapacityDesc implements Comparator<Property> {
    public int compare(Property o1, Property o2) {
        if(o1.getIdentifier().equals(o2.getIdentifier()))
            return 0;
        if(o1.getGuestsCapacity() <= o2.getGuestsCapacity()) return 1;
        return -1;
    }
}