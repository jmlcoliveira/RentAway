package property;

import java.util.Comparator;

public class ComparatorSearch implements Comparator<Property> {

    public int compare(Property o1, Property o2) {
        if (o1.getPrice() < o2.getPrice()) {
            return -1;
        }
        if (o1.getPrice() >= o2.getPrice()) {
            if (o1.getPrice() == o2.getPrice()) {
                if (o1.getGuestsCapacity() > o2.getGuestsCapacity()) {
                    return -1;
                }
                if (o1.getGuestsCapacity() <= o2.getGuestsCapacity()) {
                    if (o1.getGuestsCapacity() == o2.getGuestsCapacity()) {
                        return o1.getIdentifier().compareTo(o2.getIdentifier());
                    }
                    return 1;
                }
            }
            return 1;
        }
        return 0;
    }
}
