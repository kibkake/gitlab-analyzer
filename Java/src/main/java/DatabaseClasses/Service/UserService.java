package main.java.DatabaseClasses.Service;

import main.java.Model.ProjectSettings;
import main.java.Model.User;
import main.java.DatabaseClasses.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Helps data move between the user REST end point and the user collection. Is the "middle man" between the repository
 * and the controller REST endpoint.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void createUserAccount(User user) {
        userRepository.createUserAccount(user);
    }

    public void changePassword(User user){userRepository.changePassword(user);}

    public void changeToken(User user){userRepository.changeToken(user);}

    public void setToken(User user){userRepository.setToken(user);}

    public User retrieveUserInfo(String username) {
        return userRepository.retrieveUserInfo(username);
    }

    public String retrieveUserPassword(String username) {
        User user = userRepository.retrieveUserInfo(username);
        return user.getPassword();
    }

    public boolean retrieveUserIsLoggedIn(String username) {
        return userRepository.retrieveUserInfo(username).isLoggedIn();
    }

    public void addSetting(String username, ProjectSettings setting) {
        userRepository.addSetting(username, setting);
    }

    public List<ProjectSettings> getUserSettings(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getProjectSettings();
    }

}
