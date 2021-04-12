package main.java.ConnectToGitlab;
import main.java.Collections.Commit;
import main.java.Collections.Diff;
import main.java.Collections.Issue;
import main.java.Collections.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Calls to GitLab Api to get Commit information
 */
public class CommitConnection {

    public List<Commit> getProjectCommitsFromGitLab(int projectId) {
        User user = User.getInstance();
        String pageNumber = "1";
        RestTemplate restTemplate = new RestTemplate();
        List<Commit> commits = new ArrayList<>();
        do {
            String myUrl = user.getServerUrl() + "projects/" + projectId +
                    "/repository/commits?ref_name=master&per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Commit>> commitJSON = restTemplate.exchange(myUrl,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {
                    });

            commits.addAll(Objects.requireNonNull(commitJSON.getBody()));
            HttpHeaders headers = commitJSON.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));

        for (Commit singleCommit : commits) {
            singleCommit.setProjectId(projectId); // sets projectId if removing set project id a different way
            singleCommit.setDiffs(getSingleCommitDiffs(projectId, singleCommit.getCommitId()));
            singleCommit.calculateAndSetCommitScore(); // done after getting commits
        }
        return commits;
    }

    public static Instant getMostRecentCommitDate(int projectId) {
        User user = User.getInstance();
        List<Commit> commits = new ArrayList<>();
        String myUrl = user.getServerUrl() + "projects/" + projectId +
                "/repository/commits?ref_name=master&per_page=1&page=1&access_token=" + user.getToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Commit>> commitJSON = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {
                });
        commits.addAll(Objects.requireNonNull(commitJSON.getBody()));

        if (commits.size() != 0) {
            Commit commit = commits.get(0);
            String dateString = commit.getUpdatedAt();
            return Instant.parse(dateString);
        }
        return Instant.now();
    }

    public static List<Diff> getSingleCommitDiffs(Integer projectId, String commitHash) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String pageNumber = "1";
        List<Diff> diffs = new ArrayList<>();

        do {
            String url = user.getServerUrl() + "projects/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" +
                    "?per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Diff>> diffsJSON = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Diff>>() {
                    });

            diffs.addAll(Objects.requireNonNull(diffsJSON.getBody()));
            HttpHeaders headers = diffsJSON.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));

        for (Diff singleDiff : diffs) {
            singleDiff.calculateAndSetDiffScore();
        }
        return diffs;
    }
}
