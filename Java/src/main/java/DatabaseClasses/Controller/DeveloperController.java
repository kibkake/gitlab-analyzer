package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Service.DeveloperService;
import main.java.Collections.Developer;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v2/Project/{projectId}/Developers/")
public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("all")
    public List<Developer> getProjectDevs(@PathVariable int projectId) {
        return developerService.getProjectDevs(projectId);
    }

    @GetMapping("{devId}/mergeRequestsAndCommits")
    public List<MergeRequest> getDevMergeRequestsAndCommits(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevMergeRequestsAndCommits(projectId, devId);
    }

    @GetMapping("{devId}/mergeRequestsScores")
    public List<MergeRequestDateScore> getDevMergeRequestsScores(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevMergeRequestsScores(projectId, devId);
    }

    @GetMapping("{devId}/commitScores")
    public List<CommitDateScore> getDevCommitScores(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevCommitScores(projectId, devId);
    }

    @GetMapping("{devId}/commitArray")
    public List<CommitDateScore> getDevCommitArray(@PathVariable int projectId, @PathVariable int devId) {
        return developerService.getDevCommitScores(projectId, devId);
    }


}
