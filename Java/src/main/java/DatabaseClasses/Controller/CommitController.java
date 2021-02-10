package main.java.DatabaseClasses.Controller;
import main.java.DatabaseClasses.model.Commit;
import main.java.DatabaseClasses.model.CommitDiffs;
import main.java.DatabaseClasses.Service.CommitService;
import main.java.DatabaseClasses.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calls to GitLab Api to get Commit information
 */
@RestController
@RequestMapping(path = "api/v1/")
public class CommitController {

    @Autowired
    private RestTemplate restTemplate;

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }


    // Dates must be in YYYY-MM-DD
    // Dates must be between dates repo is open and must be dates that are not in the future from today
    @GetMapping("projects/{projectId}/commits/{sinceYYYY_MM_DD}/{untilYYYY_MM_DD}")
    public List<Commit> getProjectCommits(@PathVariable("projectId") Integer projectId,
                                          @PathVariable("sinceYYYY_MM_DD") String sinceYYYY_MM_DD,
                                          @PathVariable("untilYYYY_MM_DD") String untilYYYY_MM_DD) {
        String isoEnding = "T00:00:00-08:00";
        String since = sinceYYYY_MM_DD + isoEnding;
        String until = untilYYYY_MM_DD + isoEnding;
        if(commitService.getCommitsBetween(since, until, projectId).isEmpty()) {
            User user = User.getInstance();
            //Example: 2021-01-01T00:00:00-08:00
            //-08:00 is offset from UTC
            String myUrl = user.getServerUrl() + "/projects/" + projectId + "/repository/commits?since="
                    + sinceYYYY_MM_DD + "&until=" + untilYYYY_MM_DD + "&with_stats=true" + "&access_token="
                    + user.getToken();

            // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
            ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
            List<Commit> commits = commitsResponse.getBody();
            for (Commit singleCommit : commits) {
                singleCommit.setProjectId(projectId); // sets projectId if removing set project id a different way
                singleCommit.setDiffs(getSingleCommitDiffs(projectId, singleCommit.getId()));
            }
            commitService.addCommits(commits);
            return commits;
        } else {
            return commitService.getCommitsBetween(since, until, projectId);
        }
    }

    private List<CommitDiffs> getSingleCommitDiffs(Integer projectId, String commitHash) {
        User user = User.getInstance();
        String url = user.getServerUrl() + "/projects/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" +
                "?access_token=" + user.getToken();
        ResponseEntity<List<CommitDiffs>> commitsResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CommitDiffs>>() {});
        List<CommitDiffs> commitDiffs = commitsResponse.getBody();
        return commitDiffs;
    }

}
