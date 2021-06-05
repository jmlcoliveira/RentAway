package property;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface PrivateRoom extends Property{

    /**
     * Adds an amenity to the private room
     *
     * @param amenity the amenity
     */
    void addAmenity(String amenity);
}
