package main.java.ConnectToGitlab.Project;

import main.java.ConnectToGitlab.User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calls to GitLab Api to get Project information
 */
@RestController
public class ProjectController {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/";
    public static final String TOKEN = "cFzzy7QFRvHzfHGpgrr1";

/*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static List<Project> getProjects() {
        RestTemplate restTemplate = new RestTemplate();
        String url = MAIN_URL + "projects?simple=true";
        ResponseEntity<List<Project>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {});
        List<Project> projects = usersResponse.getBody();
        return projects;
    }
}
