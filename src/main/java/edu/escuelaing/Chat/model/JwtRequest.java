package edu.escuelaing.Chat.model;

/**
 * Model class representing a JWT authentication request.
 * It contains the username and password provided by the user during the authentication process.
 */
public class JwtRequest {

    /**
     * The username provided by the user for authentication.
     */
    private String username;

    /**
     * The password provided by the user for authentication.
     */
    private String password;

    /**
     * Gets the username provided by the user.
     *
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the authentication request.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password provided by the user.
     *
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the authentication request.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}