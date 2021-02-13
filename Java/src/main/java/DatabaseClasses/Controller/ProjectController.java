package main.java.DatabaseClasses.Controller;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.DatabaseClasses.DateScore;
import main.java.DatabaseClasses.Model.Project;
import main.java.ConnectToGitlab.Project.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class manages API mapping for functions to be called from frontend.
 * (All functions used for mapping should be placed here, except user authentication collection data.)
 */
@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    // Just a couple of examples to show how querying works, should be changed

    // Example of getting projects only with field of name
    @GetMapping("projects")
    public List<Project> getAllProjects() {
        if(projectService.getAllProjects().isEmpty()) {
            List<Project> projects = ProjectConnection.getAllProjects();
            projectService.saveNewProjects(projects);
            return projects;
        } else {
            return projectService.getAllProjects();
        }
    }


    @GetMapping("projects/{projectId}")
    public Project getProject(@PathVariable("projectId") int projectId) {
        Project project = projectService.getProject(projectId);
        if (!project.isInfoSet()) {
            projectService.setProjectInfo(projectId);
            project = projectService.getProject(projectId); // get project now that it has been modified
        }
        return project;
    }

    @GetMapping("projects/{projectId}/developers")
    public List<Developer> getProjectDevelopers(@PathVariable("projectId") int projectId) {
        return projectService.getProjectDevelopers(projectId);
    }

    @GetMapping("projects/{projectId}/issues")
    public List<Issue> getProjectIssues(@PathVariable("projectId") int projectId) {
        return projectService.getProjectIssues(projectId);
    }

    @GetMapping("projects/{projectId}/description")
    public String getProjectDescription(@PathVariable("projectId") int projectId) {
        return projectService.getProjectDescription(projectId);
    }

    @GetMapping("projects/{projectId}/mergeRequests")
    public List<MergeRequest> getProjectMergeRequests(@PathVariable("projectId") int projectId) {
        return projectService.getProjectMRs(projectId);
    }

    @GetMapping("projects/{committerName}/allCommits")
    public List<Commit> getAllUserCommits(@PathVariable("committerName") String committerName) {
        return projectService.getAllUserCommits(committerName);
    }

    @GetMapping("projects/{committerName}/numCommits")
    public int getNumUserCommits(@PathVariable("committerName") String committerName) {
        return projectService.getNumUserCommits(committerName);
    }

    @GetMapping("projects/numMergeRequests/{projectId}/{committerName}")
    public int getNumUserMergeRequests(@PathVariable("projectId") int projectId,
                                       @PathVariable("committerName") String committerName) {
        return projectService.getNumUserMergeRequests(projectId, committerName);
    }

    @GetMapping("projects/{committerName}/commitScoresPerDay")
    public List<DateScore> getUserCommitScoresPerDay(@PathVariable("committerName") String committerName) {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.now();
        // Continue here - change this function so that it accepts start and end LocalDate params
        // as additional path variables.
        return projectService.getUserCommitScoresPerDay(committerName, start, end);
    }

    @GetMapping("projects/{committerName}/totalCommitScore")
    public int totalCommitScore(@PathVariable("committerName") String committerName) {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.now();
        // Continue here - like in the above function, make these two dates path variables.
        return projectService.getTotalUserCommitScore(committerName, start, end);
    }

//    @GetMapping("projects/{projectId}/developers/{developerId}/graph")
//    public List<Developer> getDevsGraphData(@PathVariable("projectId") int projectId, @PathVariable("developerId") int developerId) {
//        return projectService.getDevsCommitsAndScores(projectId, developerId);
//    }

}

