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
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/repository/commits&with_stats=true"
                + "&access_token=" + user.getToken();

        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        return commitsResponse.getBody();
    }

    public static void getSingleCommit(String identifier) {

    }
}
