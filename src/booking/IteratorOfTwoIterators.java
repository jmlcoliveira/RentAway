package booking;

import java.util.Iterator;

/**
 * Iterator that links two other iterators
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public class IteratorOfTwoIterators implements Iterator<Booking> {

    /**
     * First iterator
     */
    private final Iterator<Booking> it1;

    /**
     * Second iterator
     */
    private final Iterator<Booking> it2;

    public IteratorOfTwoIterators(Iterator<Booking> it1, Iterator<Booking> it2) {
        this.it1 = it1;
        this.it2 = it2;
    }

    public boolean hasNext() {
        return it1.hasNext() || it2.hasNext();
    }

    public Booking next() {
        if (it1.hasNext())
            return it1.next();
        if (it2.hasNext())
            return it2.next();
        return null;
    }

}
