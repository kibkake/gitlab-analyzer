package main.java.DatabaseClasses.Controller;

import main.java.Model.Developer;
import main.java.Model.Project;
import main.java.Model.User;
import main.java.DatabaseClasses.Service.UserService;
import main.java.DatabaseClasses.DatabaseFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Sets up REST endpoints for getting and adding user info.
 */
@CrossOrigin
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

    @PostMapping(path = "changePass")
    public void changePassword(@RequestBody User user){userService.changePassword(user);}

    @PostMapping(path = "changeToken")
    public void changeToken(@RequestBody User user){userService.changeToken(user);}

    @PostMapping(path = "setToken")
    public void setToken(@RequestBody User user){userService.setToken(user);}

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


    @PostMapping("/{projectId}/setUserQuery")
    public void setStartDate(@RequestBody Map<String, String> requestBody) {
        if(requestBody.get("starttime") != null) {
            startDate = requestBody.get("starttime");
        }
    }

    @GetMapping("/{projectId}/getUserQueries")
    public List<String> getStartDate() throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date result= df1.parse(startDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result);

        String month = Integer.toString((cal.get(Calendar.MONTH)+1));
        String day = Integer.toString(cal.get(Calendar.DATE));

        if(month.length() < 2){
            month = "0" + Integer.toString((cal.get(Calendar.MONTH)+1));
        }
        if(day.length() < 2){
            day = "0" + Integer.toString(cal.get(Calendar.DATE));
        }

        List<String> date = new ArrayList<>();
        date.add(cal.get(Calendar.YEAR) + "-" + month+ "-" + day);
        return date;
    }

}
