package Commit;

import User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
// https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/8/repository/commits?since=2021-01-00T00:00:00-08:00&until=2021-02-00T00:00:00-08:00&access_token=cFzzy7QFRvHzfHGpgrr1
// https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/8/repository/commits?since=2021-01-01T00:00:00-08:00&access_token=cFzzy7QFRvHzfHGpgrr1
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
        //https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects/8/repository/commits?since=2021-01-01T00:00:00-08:00&Until=2021-01-27T00:00:00-08:00&access_token=cFzzy7QFRvHzfHGpgrr1
        // &until=2021-02-00T00:00:00-08:00&access_token=cFzzy7QFRvHzfHGpgrr1
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
