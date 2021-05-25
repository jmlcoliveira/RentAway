package users;

import java.util.Objects;

public abstract class UserClassAbs implements User{
    private final String identifier;
    private final String name;
    private final String nationality;
    private final String email;

    protected UserClassAbs(String identifier, String name, String nationality, String email){
        this.identifier = identifier;
        this.name=name;
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
