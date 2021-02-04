package main.java.ConnectToGitlab.Developer;

import main.java.ConnectToGitlab.User;
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
public class DeveloperController {

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */


    public static List<Developer> getDevelopers() {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String url = user.getServerUrl() + "users?admins=true&access_token=" + user.getToken();
        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<Developer>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        List<Developer> developers = usersResponse.getBody();
        return developers;
    }
}
