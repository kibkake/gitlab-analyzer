package main.java.ConnectToGitlab;

import main.java.Model.Issue;
import main.java.Model.Note;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class IssueConnection {

    public List<Issue> getProjectIssuesFromGitLab(int projectId) {
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

        for(Issue issue: issues) {
            issue.setNotes(getIssueNotes(issue.getProjectId(), issue.getIssueIdForASpecificProject()));
        }
        return issues;
    }

    public static Instant getMostRecentIssueUpdateDate(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        List<Issue> issues = new ArrayList<>();
        String url =user.getServerUrl() + "projects/" + projectId + "/issues"
                + "?per_page=1&order_by=updated_at&page=1&access_token=" + user.getToken();
        ResponseEntity<List<Issue>> issueResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Issue>>() {
                });
        issues.addAll(Objects.requireNonNull(issueResponse.getBody()));
        Issue issue = issues.get(0);
        String dateString = issue.getUpdatedAt();

        return Instant.parse(dateString);
    }

    private static List<Note> getIssueNotes(int projectId , int issueIForProject) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String pageNumber = "1";
        List<Note> notes = new ArrayList<>();
        do {
            String url = user.getServerUrl() + "projects/" + projectId + "/issues/" + issueIForProject + "/notes"
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
