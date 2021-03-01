package main.java.ConnectToGitlab;
import main.java.Model.Commit;
import main.java.Model.Diff;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Calls to GitLab Api to get Commit information
 */
public class CommitConnection {

    public static List<Commit> getProjectCommitsFromGitLab(int projectId) {
        User user = User.getInstance();
        String pageNumber = "1";
        List<Commit> commits = new ArrayList<>();
        do {
            String myUrl = user.getServerUrl() + "projects/" + projectId +
                    "/repository/commits?all=true&per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {
                    });

            commits.addAll(Objects.requireNonNull(commitsResponse.getBody()));
            HttpHeaders headers = commitsResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));

        for (Commit singleCommit : commits) {
            singleCommit.setProjectId(projectId); // sets projectId if removing set project id a different way
            singleCommit.setDiffs(getSingleCommitDiffs(projectId, singleCommit.getId()));
            singleCommit.calculateAndSetCommitScore(); // done after getting commits
        }
        return commits;
    }

    public static List<Diff> getSingleCommitDiffs(Integer projectId, String commitHash) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String pageNumber = "1";
        List<Diff> diffs = new ArrayList<>();

        do {
            String url = user.getServerUrl() + "projects/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" +
                    "?per_page=100&page=" + pageNumber + "&access_token=" + user.getToken();
            ResponseEntity<List<Diff>> diffsResponse = restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Diff>>() {
                    });

            diffs.addAll(Objects.requireNonNull(diffsResponse.getBody()));
            HttpHeaders headers = diffsResponse.getHeaders();
            pageNumber = headers.getFirst("X-Next-Page");
        }while (!pageNumber.equals(""));

        for (Diff singleDiff : diffs) {
            singleDiff.calculateAndSetDiffScore();
        }
        return diffs;
    }
}
