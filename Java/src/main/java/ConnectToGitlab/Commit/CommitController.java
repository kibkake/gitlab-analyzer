package ConnectToGitlab.Commit;

import ConnectToGitlab.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CommitController {
    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
    @Autowired
    private RestTemplate restTemplate;
 */

    public static void getProjectCommits() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Commit[]> responseEntity= restTemplate.getForEntity("https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/3/repository/commits?access_token=cFzzy7QFRvHzfHGpgrr1", Commit[].class);
        Commit[] users = responseEntity.getBody();
        for(int i = 0; i < users.length; i ++) {
            System.out.println(users[i]);
        }
    }

    public static void getSingleCommit(String identifier) {

    }
}
