package property;

import java.util.Comparator;

/**
 * Comparator to sort the properties their average rating.
 * In case of a draw, properties are sorted by decreasing capacity and
 * lastly by alphabetical order of the property identifier.
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class ComparatorBest implements Comparator<Property> {

    public int compare(Property o1, Property o2) {
        if (o1.getAverageRating() > o2.getAverageRating()) {
            return -1;
        }
        if (o1.getAverageRating() <= o2.getAverageRating()) {
            if (o1.getAverageRating() == o2.getAverageRating()) {
                if (o1.getCapacity() > o2.getCapacity()) {
                    return -1;
                }
                if (o1.getCapacity() <= o2.getCapacity()) {
                    if (o1.getCapacity() == o2.getCapacity()) {
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
