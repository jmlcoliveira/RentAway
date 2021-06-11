package users;

/**
 * User class with the methods shared by every user type
 *
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public abstract class UserClassAbs implements User {

    /**
     * ID of the user
     */
    private final String identifier;

    /**
     * Name of the user
     */
    private final String name;

    /**
     * Nationality of the user
     */
    private final String nationality;

    /**
     * Email of the user
     */
    private final String email;

    protected UserClassAbs(String identifier, String name, String nationality, String email) {
        this.identifier = identifier;
        this.name = name;
        this.nationality = nationality;
        this.email = email;
    }

    public final String getNationality() {
        return nationality;
    }

    public final String getName() {
        return name;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final String getEmail() {
        return email;
    }
}
