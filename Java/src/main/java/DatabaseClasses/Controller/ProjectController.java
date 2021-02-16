package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.Model.Commit;
import main.java.Model.Developer;
import main.java.Model.Issue;
import main.java.Model.MergeRequest;
import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.Model.Project;
import main.java.ConnectToGitlab.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import main.java.Functions.LocalDateFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
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

    // Wants
    // User name and 2 dates -> list of number comitts per date just list filled with zero
    // Commits per day get those commits
    // Notes -> enum
    //

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

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
        Project project = projectService.getProject(projectId);
        if (!project.isInfoSet()) {
            projectService.setProjectInfo(projectId);
        }
        return projectService.getProjectDevelopers(projectId);
    }

    @GetMapping("projects/{projectId}/issues")
    public List<Issue> getProjectIssues(@PathVariable("projectId") int projectId) {
        return projectService.getProjectIssues(projectId);
    }

    @GetMapping("setProjectInfo/{projectId}")
    public void setProjectInfo(@PathVariable int projectId) {
        projectService.setProjectInfo(projectId);

    }

    @GetMapping("projects/{projectId}/issues/{userName}/{start}/{end}")
    public List<Issue> getUserIssues(@PathVariable("projectId") int projectId, @PathVariable String end,
                                     @PathVariable String start, @PathVariable String userName) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUserIssues(projectId, userName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/description")
    public String getProjectDescription(@PathVariable("projectId") int projectId) {
        return projectService.getProjectDescription(projectId);
    }

    @GetMapping("projects/{projectId}/mergeRequests")
    public List<MergeRequest> getProjectMergeRequests(@PathVariable("projectId") int projectId) {
        return projectService.getProjectMRs(projectId);
    }

    @GetMapping("projects/{projectId}/mergeRequests/{userName}/{start}/{end}")
    public List<MergeRequest> getUsersMergeRequests(@PathVariable("projectId") int projectId,
                                                    @PathVariable("projectId") String userName,
                                                    @PathVariable("start") String start,
                                                    @PathVariable("end") String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUserMergeRequests(projectId, userName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/mergeRequestScoresPerDay/{committerName}/{start}/{end}")
    public List<MergeRequestDateScore> getUsersMergeRequestsScorePerDay(@PathVariable("projectId") int projectId,
                                                                        @PathVariable("committerName") String committerName,
                                                                        @PathVariable("start") String start,
                                                                        @PathVariable("end")String end) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUsersMergeRequestScorePerDay(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/Commits/{committerName}/{start}/{end}")
    public List<Commit> getAllUserCommits(@PathVariable("projectId") int projectId,
                                          @PathVariable("committerName") String committerName,
                                          @PathVariable("start") String start,
                                          @PathVariable("end")String end) throws ParseException {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getAllUserCommits(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/commitScoresPerDay/{committerName}/{start}/{end}")
    public List<CommitDateScore> getUserCommitScoresWithDates(@PathVariable("projectId") int projectId,
                                                           @PathVariable("committerName") String committerName,
                                                           @PathVariable("start") String start,
                                                           @PathVariable("end")String end) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUserCommitScoresPerDay(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/totalCommitScore/{committerName}")
    public double totalCommitScore(@PathVariable("projectId") int projectId,
                                @PathVariable("committerName") String committerName) {
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.now();
        //TODO: Continue here - like in the above function, make these two dates path variables.
        return projectService.getTotalUserCommitScore(projectId, committerName, start, end);
    }


}

