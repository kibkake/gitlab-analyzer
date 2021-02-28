package main.java.DatabaseClasses.Controller;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CommitController {

    private final CommitService commitService;
    //For testing
    private String s = "2021-01-11T20:59:00.000Z";
    private String e = "2021-02-22T20:59:00.000Z";
    OffsetDateTime sO = OffsetDateTime.parse(s);
    OffsetDateTime eO = OffsetDateTime.parse(e);
    Date startDate = Date.from(sO.toInstant());
    Date endDate = Date.from(eO.toInstant());

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


//        OffsetDateTime startDateWithOffSet = OffsetDateTime.parse(start);
//        OffsetDateTime endDateWithOffSet = OffsetDateTime.parse(end);
//        Date startDate = Date.from(startDateWithOffSet.toInstant());
//        Date endDate = Date.from(endDateWithOffSet.toInstant());

        return commitService.getUserCommits(projectId, startDate, endDate, committerName);
    }

//    @GetMapping("projects/{projectId}/Commitsarray/{committerName}/{start}/{end}")
//    public List<String> getCommitsArray(@PathVariable("projectId") int projectId,
//                                        @PathVariable("committerName") String committerName,
//                                        @PathVariable("start") String start,
//                                        @PathVariable("end")String end) {
//        LocalDate StartLocalTime = LocalDate.parse(start);
//        LocalDate endLocalTime = LocalDate.parse(end);
//        List<String> commitsArray = new ArrayList<>();
//        commitsArray = CommitService.getAllUserCommitsArray(projectId, committerName, StartLocalTime, endLocalTime);
//        return commitsArray;
//    }
//
    @GetMapping("projects/{projectId}/Commit/{hash}")
    public Commit getCommit(@PathVariable("projectId") int projectId,
                                   @PathVariable("hash") String hash) {

        return commitService.getCommit(projectId, hash);
    }

    @GetMapping("projects/{projectId}/Commit/scores/{userName}")
    public List<DateScore> getScores(@PathVariable int projectId, @PathVariable String userName) {
        return commitService.getScorePerDay(projectId, userName);
    }

//    @GetMapping("projects/Commit/DateScores/avg")
//    public List<Object[]> getCommit() {
//        return commitService.getScorePerDay();
//    }

//    @GetMapping("projects/{projectId}/commitScoresPerDay/{committerName}/{start}/{end}")
//    public List<DateScore> getUserCommitScoresWithDates(@PathVariable("projectId") int projectId,
//                                                        @PathVariable("committerName") String committerName,
//                                                        @PathVariable("start") String start,
//                                                        @PathVariable("end")String end) {
//
//        LocalDate StartLocalTime = LocalDate.parse(start);
//        LocalDate endLocalTime = LocalDate.parse(end);
//        return projectService.getUserCommitScoresPerDay(projectId, committerName, StartLocalTime, endLocalTime);
//    }
}
