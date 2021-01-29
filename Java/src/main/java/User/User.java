package User;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private LocalDate lastSignInAt; // do not know if this is project specific

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, LocalDate lastSignInAt) {
        this.id = id;
        this.name = name;
        this.lastSignInAt = lastSignInAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastSignInAt() {
        return lastSignInAt;
    }

    public void setLastSignInAt(LocalDate lastSignInAt) {
        this.lastSignInAt = lastSignInAt;
    }
}
