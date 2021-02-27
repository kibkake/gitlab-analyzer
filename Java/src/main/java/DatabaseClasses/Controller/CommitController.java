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
    private String s = "2021-01-11T20:59:00.000Z";
    private String e = "2021-02-22T20:59:00.000Z";

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

        OffsetDateTime startDateWithOffSet = OffsetDateTime.parse(s);
        OffsetDateTime endDateWithOffSet = OffsetDateTime.parse(e);
//        OffsetDateTime startDateWithOffSet = OffsetDateTime.parse(start);
//        OffsetDateTime endDateWithOffSet = OffsetDateTime.parse(end);
        Date startDate = Date.from(startDateWithOffSet.toInstant());
        Date endDate = Date.from(endDateWithOffSet.toInstant());

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
//        commitsArray = projectService.getAllUserCommitsArray(projectId, committerName, StartLocalTime, endLocalTime);
//        return commitsArray;
//    }
//
//    @GetMapping("projects/{projectId}/Commit/{hash}")
//    public List<Commit> getACommit(@PathVariable("projectId") int projectId,
//                                   @PathVariable("hash") String hash) {
//
//        return projectService.getCommitByHash(projectId, hash);
//    }
//
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
