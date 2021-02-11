package main.java.ConnectToGitlab.Project;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperConnection;
import main.java.ConnectToGitlab.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequestConnection;
import main.java.ConnectToGitlab.Note;
import main.java.ConnectToGitlab.User;
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

        assert projects != null;
        for(Project project: projects) {
            project.setIssues(getProjectIssues(project.getId()));
            project.setMergedRequests(MergeRequestConnection.getProjectMergeRequests(project.getId()));
            project.setCommits(getProjectCommits(project.getId()));
            project.setDevelopers(DeveloperConnection.getDevelopers());
        }
        return projects;
    }

    private static List<Issue> getProjectIssues(int projectId) {
        User user = User.getInstance();
        String url =user.getServerUrl() + "projects/" + projectId + "/issues" + "?access_token=" + user.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Issue>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Issue>>() {});

        List<Issue> issues = usersResponse.getBody();
        assert issues != null;
        for(Issue issue: issues) {
            issue.setNotes(getIssueNotes(issue.getProject_id(), issue.getIid()));
        }
        return issues;
    }

    private static List<Note> getIssueNotes(int projectId , int issueIid) {
        User user = User.getInstance();
        String url = user.getServerUrl() + "projects/" + projectId + "/issues/" + issueIid + "/notes" + "?access_token=" + user.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Note>> usersResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>() {});
        List<Note> notes = usersResponse.getBody();
        return notes;
    }

    public static List<Commit> getProjectCommits(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/repository/commits?with_stats=true" + "&access_token=" + user.getToken();
        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        return commitsResponse.getBody();
    }

    public static List<Developer> getProjectDevelopers(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/users?access_token=" + user.getToken();
        ResponseEntity<List<Developer>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        return commitsResponse.getBody();
    }
}
