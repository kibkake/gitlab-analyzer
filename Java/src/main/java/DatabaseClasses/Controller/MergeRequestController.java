package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.DatabaseClasses.Service.MergeRequestService;
import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v2/")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;
    private String isoEnding = "T00:00:00.000Z";
    
    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping("projects/{projectId}/MergeRequest/save")
    public void saveProjectMergeRequests(@PathVariable int projectId) {
        mergeRequestService.saveProjectMergeRequests(projectId);
    }

    @GetMapping("projects/{projectId}/MergeRequest/scores/{userName}/{startDate}/{endDate}")
    public List<MergeRequestDateScore> getScores(@PathVariable int projectId, @PathVariable String userName,
                                                 @PathVariable String startDate, @PathVariable String endDate) {
        LocalDate StartLocalTime = LocalDate.parse(startDate);
        LocalDate endLocalTime = LocalDate.parse(endDate);
        return mergeRequestService.getMrScorePerDay(projectId, userName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/MergeRequest/scores/total/{userName}/{startDate}/{endDate}")
    public Object getTotalMergeRequestScores(@PathVariable int projectId, @PathVariable String userName,
                                           @PathVariable String startDate, @PathVariable String endDate) {
        LocalDate StartLocalTime = LocalDate.parse(startDate);
        LocalDate endLocalTime = LocalDate.parse(endDate);
        return mergeRequestService.getTotalMergeRequestScore(projectId, userName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/MergeRequest/{committerName}/{start}/{end}")
    public List<MergeRequest> getUserMergeRequests(@PathVariable("projectId") int projectId,
                                                              @PathVariable("committerName") String committerName,
                                                              @PathVariable("start") String startDate,
                                                              @PathVariable("end")String endDate) {

        LocalDate StartLocalTime = LocalDate.parse(startDate);
        LocalDate endLocalTime = LocalDate.parse(endDate);
        return mergeRequestService.getUserMergeRequests(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/mergeRequest/{mrId}")
    public MergeRequest getSingleMergeRequest(@PathVariable int mrId, @PathVariable int projectId) {
        return mergeRequestService.getMergeRequest(projectId, mrId);
    }

    @GetMapping("projects/{projectId}/mergeRequests")
    public List<MergeRequest> getProjectMergeRequests(@PathVariable("projectId") int projectId) {
        return mergeRequestService.getProjectMRs(projectId);
    }

    @GetMapping("projects/{projectId}/mergeRequests/{commitHash}")
    public MergeRequest getCommitMr(@PathVariable("projectId") int projectId,
                     @PathVariable("commitHash") String commitHash) {

        return mergeRequestService.getMrByCommitHash(projectId, commitHash);
    }
}
