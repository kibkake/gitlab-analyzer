package main.java.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import java.util.ArrayList;
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
    private List<ProjectSettings> projectSettings;


    private static User singleton = new User( );

    public static User getInstance() {
        if(singleton == null) {
            singleton = new User();
        }
        return singleton;
    }

    public User() {
        projectSettings = new ArrayList<>();
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

    public List<ProjectSettings> getProjectSettings() {
        return projectSettings;
    }

    public void setProjectSettings(List<ProjectSettings> projectSettings) {
        this.projectSettings = projectSettings;
    }

    public static User getSingleton() {
        return singleton;
    }

    public static void setSingleton(User singleton) {
        User.singleton = singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return (isLoggedIn == user.isLoggedIn && Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(serverUrl, user.serverUrl)
                && Objects.equals(token, user.token)
                && Objects.equals(name, user.name)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password));
    }
}
