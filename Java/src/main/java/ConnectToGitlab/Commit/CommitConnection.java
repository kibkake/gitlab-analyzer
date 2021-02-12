package main.java.ConnectToGitlab.Commit;
import main.java.ConnectToGitlab.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Calls to GitLab Api to get Commit information
 */
public class CommitConnection {

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
        @Autowired
        private RestTemplate restTemplate;
     */

    public static List<Commit> getProjectCommits(int projectId) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/repository/commits?access_token=" + user.getToken();
        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        List<Commit> commits = commitsResponse.getBody();
        for (Commit singleCommit : commits) {
            singleCommit.setProjectId(projectId); // sets projectId if removing set project id a different way
            singleCommit.setDiffs(getSingleCommitDiffs(projectId, singleCommit.getId()));
            singleCommit.setCommitScore(singleCommit.calculateCommitScore()); // done after getting commits
        }
        return commits;
    }

    private static List<CommitDiff> getSingleCommitDiffs(Integer projectId, String commitHash) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String url = user.getServerUrl() + "/projects/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" +
                "?access_token=" + user.getToken();
        ResponseEntity<List<CommitDiff>> commitsResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CommitDiff>>() {});
        List<CommitDiff> commitDiffs = commitsResponse.getBody();
        for (CommitDiff singleDiff : commitDiffs) {
            singleDiff.calculateCommitScoreSingleDiff();
        }
        return commitDiffs;
    }
}
