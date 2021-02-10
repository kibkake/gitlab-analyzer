package main.java.ConnectToGitlab.Commit;
import main.java.DatabaseClasses.User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Calls to GitLab Api to get Commit information
 */
@Controller
@RequestMapping(path = "api/v1/commits")
public class CommitController {

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
        @Autowired
        private RestTemplate restTemplate;
     */
    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }


    // Dates must be in YYYY-MM-DD
    // Dates must be between dates repo is open and must be dates that are not in the future from today
    public static List<Commit> getProjectCommits(int projectId, String sinceYYYY_MM_DD, String untilYYYY_MM_DD) {
        User user = User.getInstance();
        //Example: 2021-01-01T00:00:00-08:00
        //-08:00 is offset from UTC
        String isoEnding = "T00:00:00-08:00";
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/repository/commits?since=" + sinceYYYY_MM_DD + isoEnding + "&until=" + untilYYYY_MM_DD
               + isoEnding + "&with_stats=true" + "&access_token=" + user.getToken();

        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        return commitsResponse.getBody();
    }

    public static void getSingleCommit(String identifier) {

    }
}
