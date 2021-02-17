package main.java.ConnectToGitlab;

import main.java.Model.Issue;
import main.java.Model.Note;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class IssueConnection {

    public static List<Issue> getProjectIssues(int projectId) {
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
        for(Note note: notes) {
            note.setIssueNote(true);
            note.setWordCount(note.countWords(note.getBody()));
        }
        return notes;
    }
}
