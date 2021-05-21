package property;

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
