package main.java.ConnectToGitlab;

import main.java.Model.Commit;
import main.java.Model.Issue;
import main.java.Model.Note;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IssueConnection {

    public static List<Issue> getProjectIssues(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String pageNumber = "1";
        List<Issue> issues = new ArrayList<>();
        do {
            String url =user.getServerUrl() + "projects/" + projectId + "/issues"
                    + "?per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Issue>> issueResponse = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Issue>>() {
                    });

            issues.addAll(Objects.requireNonNull(issueResponse.getBody()));
            HttpHeaders headers = issueResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));

        assert issues != null;
        for(Issue issue: issues) {
            issue.setNotes(getIssueNotes(issue.getProject_id(), issue.getIid()));
        }
        return issues;
    }

    private static List<Note> getIssueNotes(int projectId , int issueIid) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String pageNumber = "1";
        List<Note> notes = new ArrayList<>();
        do {
            String url = user.getServerUrl() + "projects/" + projectId + "/issues/" + issueIid + "/notes"
                    + "?per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Note>> noteResponse = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Note>>() {
                    });

            notes.addAll(Objects.requireNonNull(noteResponse.getBody()));
            HttpHeaders headers = noteResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));
        for(Note note: notes) {
            note.setIssueNote(true);
            note.setWordCount(note.countWords(note.getBody()));
        }
        return notes;
    }
}
