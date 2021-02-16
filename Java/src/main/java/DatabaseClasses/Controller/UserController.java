package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.User;
import main.java.DatabaseClasses.Service.UserService;
import main.java.DatabaseClasses.DatabaseFunctions;
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

    @GetMapping("userAuthenticated/{username}/{password}")
    public boolean userAuthenticated(@PathVariable("username") String username,
                                     @PathVariable("password") String password) {
        return DatabaseFunctions.isUserAuthenticated(username, password);
    }

    @GetMapping("userEncryptedPassword/{username}")
    public String userEncryptedPassword(@PathVariable("username") String username) {
        return userService.retrieveUserPassword(username);
    }

    @GetMapping("getUserLoggedInStatus/{username}")
    public boolean getUserLoggedInStatus(@PathVariable("username") String username) {
        return DatabaseFunctions.getUserLoggedInStatus(username);
    }


    @RequestMapping("setUserLoggedInStatus/{username}/{isLoggedIn}")
    public void setUserLoggedInStatus(@PathVariable("username") String username,
                                      @PathVariable("isLoggedIn") boolean isLoggedIn) {
        DatabaseFunctions.setUserLoggedInStatus(username, isLoggedIn);
    }


//    @GetMapping(value = "users/{userName}")
//    public Optional<User> getUsers(@PathVariable("userName") String userName) {
//        return userRepository.findById(userName);
//    }

}
