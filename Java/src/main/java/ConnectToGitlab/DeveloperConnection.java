package main.java.ConnectToGitlab;

import main.java.Model.Developer;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calls to GitLab Api to get user information
 */
@RestController
public class DeveloperConnection {

    public List<Developer> getProjectDevelopersFromGitLab(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/users?access_token=" + user.getToken();
        ResponseEntity<List<Developer>> developerResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        List<Developer> devs = developerResponse.getBody();
        return devs;
    }

}
