package main.java.ConnectToGitlab.Project;

import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.Model.Project;
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
public class ProjectConnection {

/*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static List<Project> getAllProjects() {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String url = user.getServerUrl() + "projects?simple=true&access_token=" + user.getToken();
        ResponseEntity<List<Project>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {});
        List<Project> projects = usersResponse.getBody();

//        assert projects != null;
//        for(Project project: projects) {
//            project.setIssues(getProjectIssues(project.getId()));
//            project.setMergedRequests(MergeRequestConnection.getProjectMergeRequests(project.getId()));
//            project.setCommits(getProjectCommits(project.getId()));
//            project.setDevelopers(DeveloperConnection.getDevelopers());
//        }
        return projects;
    }
}
