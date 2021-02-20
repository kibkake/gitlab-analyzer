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

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    //Override timeouts in request factory
    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(12_000);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(12_000);
        return clientHttpRequestFactory;
    }

    public List<Developer> getProjectDevelopersFromGitLab(int projectId) {
        User user = User.getInstance();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/users?access_token=" + user.getToken();
        ResponseEntity<List<Developer>> developerResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        List<Developer> devs = developerResponse.getBody();
        return devs;
    }

}
