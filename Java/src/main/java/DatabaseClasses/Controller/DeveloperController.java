package main.java.DatabaseClasses.Controller;

import main.java.Collections.Commit;
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
    public List<String> getProjectDevs(@PathVariable int projectId) {
        return developerService.getProjectDevUsernames(projectId);
    }

    @GetMapping("{username}/mergeRequestsAndCommits")
    public List<MergeRequest> getDevMergeRequestsAndCommits(@PathVariable int projectId, @PathVariable String username) {
        return developerService.getDevMergeRequestsAndCommits(projectId, username);
    }

    @GetMapping("{devId}/mergeRequestsScores")
    public List<MergeRequestDateScore> getDevMergeRequestsScores(@PathVariable int projectId, @PathVariable String username) {
        return developerService.getDevMergeRequestsScores(projectId, username);
    }

    @GetMapping("{username}/commitScores")
    public List<CommitDateScore> getDevCommitScores(@PathVariable int projectId, @PathVariable String username) {
        return developerService.getDevCommitScores(projectId, username);
    }

    @GetMapping("{username}/commitArray")
    public List<CommitDateScore> getDevCommitArray(@PathVariable int projectId, @PathVariable String username) {
        return developerService.getDevCommitScores(projectId, username);
    }

    @GetMapping("{username}/commits")
    public List<Commit> getDevCommits(@PathVariable int projectId, @PathVariable String username) {
        return developerService.getDevCommits(projectId, username);
    }

}
