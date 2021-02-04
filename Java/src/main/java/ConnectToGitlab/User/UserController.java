package main.java.ConnectToGitlab.User;

import main.java.ConnectToGitlab.Commit.Commit;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calls to GitLab Api to get user information
 */
@RestController
public class UserController {
    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/";
    public static final String TOKEN = "cFzzy7QFRvHzfHGpgrr1";

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static List<User> getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        String url = MAIN_URL + "users?admins=true&access_token=" + TOKEN;
        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> users = usersResponse.getBody();
        return users;
    }
}
