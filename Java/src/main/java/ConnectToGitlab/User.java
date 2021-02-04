package main.java.ConnectToGitlab;

/**
 * Used to hold the server info and the users authentication so it is not hard codded in different files and can
 *  be updated easily
 *  User in this context refers to the user of the product we are building
 */

public class User {

    private String serverUrl;
    private String token;
    private String name;
    private String email;

    private static User singleton = new User( );

    public static User getInstance() {
        if(singleton == null) {
            singleton = new User();
        }
        return singleton;
    }

    private User() {
    }

    private User(String serverUrl, String token, String name, String email) {
        this.serverUrl = serverUrl;
        this.token = token;
        this.name = name;
        this.email = email;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
