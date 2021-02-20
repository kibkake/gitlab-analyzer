package main.java.ConnectToGitlab;

import main.java.Model.Developer;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

    public static List<Developer> getProjectDevelopersFromGitLab(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/users?access_token=" + user.getToken();
        ResponseEntity<List<Developer>> developerResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        List<Developer> devs = developerResponse.getBody();
        return devs;
    }
    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 12000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

}
