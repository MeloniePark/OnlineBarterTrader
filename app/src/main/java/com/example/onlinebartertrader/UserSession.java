package com.example.onlinebartertrader;

/**
 * The UserSession class is used to store the currently logged in user's information throughout the application.
 */
public class UserSession {

    private static UserSession instance;
    private String user;

    /**
     * Creates a new UserSession instance.
     */
    UserSession() {
    }

    /**
     * Returns the instance of UserSession.
     * @return The UserSession instance.
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets the username of the currently logged in user.
     * @return The username of the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the username of the currently logged in user.
     * @param user The username of the user.
     */
    public void setUser(String user) {
        this.user = user;
    }
}
