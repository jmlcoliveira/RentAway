package users;

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
}
