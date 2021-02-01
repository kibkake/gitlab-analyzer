package main.java.ConnectToGitlab.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


// get commits, diff files, issues started by, commentingon , MR there apartof
@RestController
public class UserController {
    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static User[] getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> responseEntity= restTemplate.getForEntity("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/users?access_token=cFzzy7QFRvHzfHGpgrr1", User[].class);
        User[] users = responseEntity.getBody();
        return users;
    }
}
