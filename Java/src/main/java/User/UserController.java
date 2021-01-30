package main.java.User;

import main.java.User.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static void getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> responseEntity= restTemplate.getForEntity("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/users?access_token=cFzzy7QFRvHzfHGpgrr1", User[].class);
        User[] users = responseEntity.getBody();
        for(int i = 0; i < users.length; i ++) {
            System.out.println(users[i]);
        }
    }
}
