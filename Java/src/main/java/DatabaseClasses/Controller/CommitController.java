package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Service.CommitService;
import main.java.Collections.Commit;
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

    @GetMapping("projects/{projectId}/Commits/{hash}")
    public Commit getCommit(@PathVariable("projectId") int projectId,
                            @PathVariable("hash") String hash) {

        return commitService.getCommit(projectId, hash);
    }
}
