package main.java.ConnectToGitlab.Project;

import main.java.ConnectToGitlab.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequestConnection;
import main.java.ConnectToGitlab.Note;
import main.java.ConnectToGitlab.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
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
        String url = user.getServerUrl() + "projects?simple=true";
        ResponseEntity<List<Project>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Project>>() {});
        List<Project> projects = usersResponse.getBody();

        for(Project project: projects) {
            project.setIssues(getAllIssues(project.getId()));
            project.setMergedRequests(MergeRequestConnection.getProjectMergeRequests(project.getId()));
        }
        return projects;
    }

    private static List<Issue> getAllIssues(int projectId) {
        User user = User.getInstance();
        String url =user.getServerUrl() + projectId + "/issues" + "?access_token=" + user.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Issue>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Issue>>() {});
        List<Issue> issues = usersResponse.getBody();

        for(Issue issue: issues) {
            issue.setNotes(getAllNotes(issue.getProject_id(), issue.getIid()));
        }
        return issues;
    }

    private static List<Note> getAllNotes(int projectId , int issueIid) {
        User user = User.getInstance();
        String url = user.getServerUrl() + "/" + projectId + "/issues/" + issueIid + "/notes" + "?access_token=" + user.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Note>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>() {});
        List<Note> notes = usersResponse.getBody();
        return notes;
    }
}
