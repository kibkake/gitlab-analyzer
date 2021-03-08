package main.java.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Holds the user and login information
 */

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String serverUrl;
    private String token;
    private String name;
    private String email;
    private String password;
    private boolean isLoggedIn;
    private List<UserQuery> userQueries;


    private static User singleton = new User( );

    public static User getInstance() {
        if(singleton == null) {
            singleton = new User();
        }
        return singleton;
    }

    public User() {
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
        return this.token;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<UserQuery> getUserQueries() {
        return userQueries;
    }

    public void setUserQueries(List<UserQuery> userQueries) {
        this.userQueries = userQueries;
    }

    public static User getSingleton() {
        return singleton;
    }

    public static void setSingleton(User singleton) {
        User.singleton = singleton;
    }
}
