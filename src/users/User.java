package users;

/**
 * @author Guilherme Pocas 60236, Joao Oliveira 61052
 */
public interface User{

    /**
     * Gets the ID of the user
     *
     * @return ID of the user
     */
    String getIdentifier();

    /**
     * Gets the name of the user
     *
     * @return name of the user
     */
    String getName();

    /**
     * Gets the email of the user
     *
     * @return email of the user
     */
    String getEmail();

    /**
     * Gets the nationality of the user
     *
     * @return nationality of the user
     */
    String getNationality();

}
