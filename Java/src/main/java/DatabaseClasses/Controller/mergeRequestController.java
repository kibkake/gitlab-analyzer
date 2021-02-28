package main.java.DatabaseClasses.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class mergeRequestController {

    @GetMapping("projects/{projectId}/Commits/save")
    public void saveProjectCommits(@PathVariable int projectId) {
        commitService.saveProjectCommits(projectId);
    }
}
