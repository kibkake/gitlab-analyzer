package main.java.Commit;

import Commit.Commit;
import main.java.User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class CommitController {
    private static String serverUrl = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/";
    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
        @Autowired
        private RestTemplate restTemplate;
     */

    // Dates must be in YYYY-MM-DD
    // Dates must be between dates repo is open and must be dates that are not in the future from today
    public static List<Commit> getProjectCommits(int id, String sinceDate, String untilDate) {
        //Example: 2021-01-01T00:00:00-08:00
        //-08:00 is offset from UTC
        String isoEnding = "T00:00:00-08:00";
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = serverUrl + id +"/repository/commits?since=" + sinceDate + isoEnding + "&until=" + untilDate
               + isoEnding + "&access_token=cFzzy7QFRvHzfHGpgrr1";

//                serverUrl + id +
//                "/repository/commits?" + "since=" + sinceDate + isoEnding + "&access_token=cFzzy7QFRvHzfHGpgrr1"

        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        System.out.println(myUrl);
        ResponseEntity<List<Commit>> rateResponse = restTemplate.exchange(myUrl,
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
