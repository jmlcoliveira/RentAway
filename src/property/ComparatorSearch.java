package property;

import java.util.Comparator;

/**
 * Comparator to sort the properties by increasing price. In case of a draw, properties are sorted by decreasing capacity
 * and lastly by alphabetical order of the property identifier.
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
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
