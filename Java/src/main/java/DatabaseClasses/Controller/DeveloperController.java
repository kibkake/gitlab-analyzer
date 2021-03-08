package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.DatabaseClasses.Service.DeveloperService;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v2/Project{projectId}/Developer/{devId}")
public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("mergeRequestsAndCommits")
    public List<MergeRequest> getDevMergeRequestsAndCommits(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevMergeRequestsAndCommits(projectId, devId);
    }

    @GetMapping("mergeRequestsScores")
    public List<MergeRequestDateScore> getDevMergeRequestsScores(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevMergeRequestsScores(projectId, devId);
    }

    @GetMapping("commitScores")
    public List<CommitDateScore> getDevCommitScores(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevCommitScores(projectId, devId);
    }


}
