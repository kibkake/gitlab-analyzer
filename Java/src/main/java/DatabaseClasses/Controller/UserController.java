package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.model.User;
import main.java.DatabaseClasses.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sets up REST endpoints for getting and adding user info.
 */
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
       return userService.getUsers();
    }

    @PostMapping
    public void createUserAccount(@RequestBody User user) {
        userService.createUserAccount(user);
    }

    @GetMapping(path = "{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.retrieveUserInfo(username);
    }

//    @GetMapping(value = "users/{userName}")
//    public Optional<User> getUsers(@PathVariable("userName") String userName) {
//        return userRepository.findById(userName);
//    }

}
