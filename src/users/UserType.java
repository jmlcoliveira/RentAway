package users;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public enum UserType {
    GUEST("guest"),
    HOST("host"),
    UNKNOWN("unknown");

    private final String type;

    UserType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
