package outputmessages;

/**
 * Class containing messages to be shown when the requested information does not exist
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public abstract class Empty {

    public static final String NO_USERS = "No users registered.";

    public static final String NO_REJECTED_BOOKINGS = "Host %s has not rejected any booking.\n";

    public static final String GUEST_HAS_NO_BOOKINGS = "Guest %s has no bookings.\n";

    public static final String HOST_HAS_NO_PROPERTIES = "Host %s has no registered properties.\n";

    public static final String PROPERTY_NOT_FOUND = "No property was found in %s.\n";

    public static final String PROPERTY_HAS_NO_STAYS = "Property %s does not have any stays.\n";

    public static final String NO_GLOBE_TROTTER = "No globe trotter.";
}
