package main.java.ConnectToGitlab;

import main.java.Model.Diff;
import main.java.Model.Project;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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


    public List<Project> getAllProjectsFromGitLab() {
        User user = User.getInstance();
        String pageNumber = "1";
        List<Project> projects = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        do {
            String url = user.getServerUrl() + "projects?simple=true"
                     + "&per_page=100&page=" + pageNumber +"&membership=true"
                    + "&access_token=" + user.getToken();
            ResponseEntity<List<Project>> projectResponse = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {
                    });

            projects.addAll(Objects.requireNonNull(projectResponse.getBody()));
            HttpHeaders headers = projectResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
            System.out.println(pageNumber);
        } while (!pageNumber.equals(""));

        return projects;
    }
}
