package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Service.CommitService;
import main.java.DatabaseClasses.Service.MergeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    @Autowired
    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @GetMapping("projects/{projectId}/MergeRequest/save")
    public void saveProjectCommits(@PathVariable int projectId) {
        mergeRequestService.saveProjectMergeRequests(projectId);
    }

    @GetMapping("projects/{projectId}/MergeRequest/scores/{userName}")
    public List<DateScore> getScores(@PathVariable int projectId, @PathVariable String userName) {
        return mergeRequestService.getScorePerDay(projectId, userName);
    }
}