package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Service.CommitService;
import main.java.DatabaseClasses.Service.ProjectService;
import main.java.Model.Commit;
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
@RequestMapping("api/v1/")
public class CommitController {

    private final CommitService commitService;
    private String isoEnding = "T00:00:00.000Z";

    @Autowired
    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("projects/{projectId}/Commits/save")
    public void saveProjectCommits(@PathVariable int projectId) {
        commitService.saveProjectCommits(projectId);
    }

    @GetMapping("projects/{projectId}/Commits/{committerName}/{start}/{end}")
    public List<Commit> getAllUserCommits(@PathVariable("projectId") int projectId,
                                          @PathVariable("committerName") String committerName,
                                          @PathVariable("start") String start,
                                          @PathVariable("end")String end) {


        OffsetDateTime startDateWithOffSet = OffsetDateTime.parse(start + isoEnding);
        OffsetDateTime endDateWithOffSet = OffsetDateTime.parse(end + isoEnding);
        Date startDate = Date.from(startDateWithOffSet.toInstant());
        Date endDate = Date.from(endDateWithOffSet.toInstant());

        return commitService.getUserCommits(projectId, startDate, endDate, committerName);
    }

    @GetMapping("projects/{projectId}/commitScoresPerDay/{committerName}/{start}/{end}")
    public List<CommitDateScore> getUserCommitScoresWithDates(@PathVariable("projectId") int projectId,
                                                              @PathVariable("committerName") String committerName,
                                                              @PathVariable("start") String start,
                                                              @PathVariable("end")String end) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return commitService.getScorePerDay(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/Commitsarray/{committerName}/{start}/{end}")
    public List<CommitDateScore> getCommitsArray(@PathVariable("projectId") int projectId,
                                           @PathVariable("committerName") String committerName,
                                           @PathVariable("start") String start,
                                           @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return commitService.getAllUserCommitsArray(projectId, committerName, StartLocalTime, endLocalTime);
    }


    @GetMapping("projects/{projectId}/Commit/{hash}")
    public Commit getCommit(@PathVariable("projectId") int projectId,
                            @PathVariable("hash") String hash) {

        return commitService.getCommit(projectId, hash);
    }
}
