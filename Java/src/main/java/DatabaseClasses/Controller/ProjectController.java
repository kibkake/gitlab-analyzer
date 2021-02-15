package main.java.DatabaseClasses.controller;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.DatabaseClasses.CommitDateScore;
import main.java.DatabaseClasses.Model.Project;
import main.java.ConnectToGitlab.Project.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import main.java.Functions.LocalDateFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

    @GetMapping("projects/allCommits/{projectId}/{committerName}")
    public List<Commit> getAllUserCommits(@PathVariable("projectId") int projectId,
                                          @PathVariable("committerName") String committerName) {
        return projectService.getAllUserCommits(projectId, committerName);
    }

    @GetMapping("projects/numCommits/{projectId}/{committerName}")
    public int getNumUserCommits(@PathVariable("projectId") int projectId,
                                 @PathVariable("committerName") String committerName) {
        return projectService.getNumUserCommits(projectId, committerName);
    }

    @GetMapping("projects/numMergeRequests/{projectId}/{committerName}")
    public int getNumUserMergeRequests(@PathVariable("projectId") int projectId,
                                       @PathVariable("committerName") String committerName) {
        return projectService.getNumUserMergeRequests(projectId, committerName);
    }

    @GetMapping("projects/commitScoresPerDay/{projectId}/user/{committerName}/start/{start}/end/{end}")
    public List<CommitDateScore> getUserCommitScoresPerDay(@PathVariable("projectId") int projectId,
                                                           @PathVariable("committerName") String committerName,
                                                           @PathVariable("start") String start,
                                                           @PathVariable("end")String end) throws ParseException {


        Date startDate= new SimpleDateFormat("dd-MM-yyyy").parse(start);
        Date endDate= new SimpleDateFormat("dd-MM-yyyy").parse(end);

        LocalDate StartLocalTime = LocalDateFunctions.convertDateToLocalDate(startDate);
        LocalDate endLocalTime = LocalDateFunctions.convertDateToLocalDate(endDate);

        return projectService.getUserCommitScoresPerDay(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/totalCommitScore/{committerName}")
    public double totalCommitScore(@PathVariable("projectId") int projectId,
                                @PathVariable("committerName") String committerName) {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.now();
        // Continue here - like in the above function, make these two dates path variables.
        return projectService.getTotalUserCommitScore(projectId, committerName, start, end);
    }

    @GetMapping("projects/{pojectId}/user/{committerName}/mergeRequests")
    public List<MergeRequest> getUserMergeRequests(@PathVariable("projectId") int pojectId,
                                     @PathVariable("committerName") String committerName) {
        return projectService.getUserMergeRequests(pojectId, committerName);
    }
}

