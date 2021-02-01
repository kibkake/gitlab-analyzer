package Commit;

import User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CommitController {
    private static String url = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/3/repository/commits?access_token=cFzzy7QFRvHzfHGpgrr1";
    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
        @Autowired
        private RestTemplate restTemplate;
     */

    public static List<Commit> getProjectCommits() {
        RestTemplate restTemplate = new RestTemplate();
        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<Commit>> rateResponse = restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        List<Commit> commits = rateResponse.getBody();
        for(int i = 0; i < commits.size(); i ++) {
            System.out.println(commits.get(i));
        }
        return commits;
    }

    public static void getSingleCommit(String identifier) {

    }
}
