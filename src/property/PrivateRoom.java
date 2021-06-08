package property;

/**
 * Type of rental property, with a number of amenities the guests have access to
 *
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
