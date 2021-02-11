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
public class DeveloperConnection {

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static List<Developer> getProjectDevelopers(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/users?access_token=" + user.getToken();
        ResponseEntity<List<Developer>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        return commitsResponse.getBody();
    }


}
