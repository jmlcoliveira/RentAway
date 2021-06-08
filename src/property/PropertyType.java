package property;

/**
 * Enum with every type of property
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum PropertyType {
    ENTIRE_PLACE("entire place"), PRIVATE_ROOM("private room"), UNKNOWN("unknown");

    private final String type;

    PropertyType(String type){
        this.type = type;
    }

    public String getTypeValue() {
        return type;
    }
}
