package main.java.ConnectToGitlab;

import main.java.Model.Diff;
import main.java.Model.Project;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Calls to GitLab Api to get Project information
 */
@RestController
public class ProjectConnection {

/*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static List<Project> getAllProjectsFromGitLab() {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        String pageNumber = "1";
        List<Project> projects = new ArrayList<>();
        do {
            String url = user.getServerUrl() + "projects?simple=true"
                    + "? + per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Project>> projectResponse = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {
                    });

            projects.addAll(Objects.requireNonNull(projectResponse.getBody()));
            HttpHeaders headers = projectResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        } while (!pageNumber.equals(""));

        return projects;
    }
    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 12000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
