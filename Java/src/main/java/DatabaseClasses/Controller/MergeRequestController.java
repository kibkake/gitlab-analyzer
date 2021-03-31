package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Service.MergeRequestService;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @GetMapping("projects/{projectId}/mergeRequestByCommit/{commitHash}")
    public MergeRequest getCommitMr(@PathVariable("projectId") int projectId,
                     @PathVariable("commitHash") String commitHash) {

        return mergeRequestService.getMrByCommitHash(projectId, commitHash);
    }
}
