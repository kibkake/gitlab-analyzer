package main.java.DatabaseClasses.Service;

import main.java.DatabaseClasses.Model.User;
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
}
