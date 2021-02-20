package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.Model.*;
import main.java.ConnectToGitlab.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("projects/{projectId}/MRsAndCommitScoresPerDay/{committerName}/{start}/{end}")
    public List<DateScore> getUserMRsAndCommitScorePerDay(@PathVariable("projectId") int projectId,
                                                                        @PathVariable("committerName") String committerName,
                                                                        @PathVariable("start") String start,
                                                                        @PathVariable("end")String end) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getScoresPerDayForMRsAndCommits(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/Commits/{committerName}/{start}/{end}")
    public List<Commit> getAllUserCommits(@PathVariable("projectId") int projectId,
                                          @PathVariable("committerName") String committerName,
                                          @PathVariable("start") String start,
                                          @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getAllUserCommits(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/Commitsarray/{committerName}/{start}/{end}")
    public List<String> getCommitsArray(@PathVariable("projectId") int projectId,
                                           @PathVariable("committerName") String committerName,
                                           @PathVariable("start") String start,
                                           @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        List<String> commitsArray = new ArrayList<>();
        commitsArray = projectService.getAllUserCommitsArray(projectId, committerName, StartLocalTime, endLocalTime);
        return commitsArray;
    }

    @GetMapping("projects/{projectId}/Commit/{hash}")
    public List<Commit> getACommit(@PathVariable("projectId") int projectId,
                                   @PathVariable("hash") String hash) {

        return projectService.getCommitByHash(projectId, hash);
    }

    @GetMapping("projects/{projectId}/commitScoresPerDay/{committerName}/{start}/{end}")
    public List<DateScore> getUserCommitScoresWithDates(@PathVariable("projectId") int projectId,
                                                        @PathVariable("committerName") String committerName,
                                                        @PathVariable("start") String start,
                                                        @PathVariable("end")String end) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUserCommitScoresPerDay(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/topTenUserNotes/{committerName}/{start}/{end}")
    public List<Note> getTopTenUserNotes(@PathVariable("projectId") int projectId,
                                         @PathVariable("committerName") String committerName,
                                         @PathVariable("start") String start,
                                         @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getTopTenUserNotes(projectId, committerName, StartLocalTime, endLocalTime);
    }

    //TODO: This doesn't work?
    @GetMapping("projects/{projectId}/totalCommitScore/{committerName}/{start}/{end}")
    public double totalCommitScore(@PathVariable("projectId") int projectId,
                                @PathVariable("committerName") String committerName,
                                @PathVariable("start") String start,
                                   @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getTotalUserCommitScore(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/commit/{commitId}")
    public Commit getCommit(@PathVariable String commitId, @PathVariable int projectId) {
        return projectService.getCommit(projectId, commitId);
    }

    @GetMapping("projects/{projectId}/mergeRequest/{mrId}")
    public MergeRequest getMergeRequest(@PathVariable int mrId, @PathVariable int projectId) {
        return projectService.getMergeRequest(projectId, mrId);
    }


    //TODO: to get dates from frontend setting
    @PostMapping("/dates/start")
    public LocalDate createStartDate(@RequestBody LocalDate date) {
        // probably should be parsed here, input is not likely to be the localdate object
        return date;
    }

    @PostMapping("/dates/end")
    public LocalDate createEndDate(@RequestBody LocalDate date) {
        return date;
    }

    @PostMapping("/setdate")
    public String setDate(@RequestBody Map<String, String> requestBody) {

        if(requestBody.get("starttime") != null) {
            System.out.println(requestBody.get("starttime"));
        }
        System.out.println("got it!!");
        return "in";

    }
}

