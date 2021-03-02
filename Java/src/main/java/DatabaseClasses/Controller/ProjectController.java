package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Model.AllScores;
import main.java.Model.*;
import main.java.ConnectToGitlab.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
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
    private String startDate = "2021-01-11T20:59:00.000Z";
    private String endDate = "2021-02-22T20:59:00.000Z";


    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects")
    public List<Project> getAllProjects() {
        if(projectService.getAllProjects().isEmpty()) {
            List<Project> projects = new ProjectConnection().getAllProjectsFromGitLab();
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

    // can only be used on very small projects
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


    @GetMapping("projects/{projectId}/MRsAndCommitScoresPerDay/{committerName}/{start}/{end}")
    public List<DateScore> getUserMRsAndCommitScorePerDay(@PathVariable("projectId") int projectId,
                                                                        @PathVariable("committerName") String committerName,
                                                                        @PathVariable("start") String start,
                                                                        @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getScoresPerDayForMRsAndCommits(projectId, committerName, StartLocalTime, endLocalTime);
    }

//
//    @GetMapping("projects/{projectId}/Commitsarray/{committerName}/{start}/{end}")
//    public List<String> getCommitsArray(@PathVariable("projectId") int projectId,
//                                           @PathVariable("committerName") String committerName,
//                                           @PathVariable("start") String start,
//                                           @PathVariable("end")String end) {
//        LocalDate StartLocalTime = LocalDate.parse(start);
//        LocalDate endLocalTime = LocalDate.parse(end);
//        List<String> commitsArray = new ArrayList<>();
//        commitsArray = projectService.getAllUserCommitsArray(projectId, committerName, StartLocalTime, endLocalTime);
//        return commitsArray;
//    }





    // TODO: should be checked, doesn't return data
    @GetMapping("projects/{projectId}/topTenUserNotes/{committerName}/{start}/{end}")
    public List<Note> getTopTenUserNotes(@PathVariable("projectId") int projectId,
                                         @PathVariable("committerName") String committerName,
                                         @PathVariable("start") String start,
                                         @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getTopUserNotes(projectId, committerName, StartLocalTime, endLocalTime, 10, true);
    }

    @GetMapping("projects/{projectId}/allUserNotes/{committerName}/{start}/{end}")
    public List<Note> getAllUserNotes(@PathVariable("projectId") int projectId,
                                         @PathVariable("committerName") String committerName,
                                         @PathVariable("start") String start,
                                         @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getUserNotes(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/totalCommitScore/{committerName}/{start}/{end}")
    public double totalCommitScore(@PathVariable("projectId") int projectId,
                                   @PathVariable("committerName") String committerName,
                                   @PathVariable("start") String start,
                                   @PathVariable("end") String end) {
        LocalDate startLocalDate = LocalDate.parse(start);
        LocalDate endLocalDate = LocalDate.parse(end);
        return projectService.getTotalUserCommitScore(projectId, committerName,
                                                      startLocalDate, endLocalDate);
    }


    @GetMapping("projects/{projectId}/mergeRequest/{mrId}")
    public MergeRequest getMergeRequest(@PathVariable int mrId, @PathVariable int projectId) {
        return projectService.getMergeRequest(projectId, mrId);
    }

    @GetMapping("projects/{projectId}/allTotalScores/{username}/{start}/{end}")
    public AllScores allTotalScores(@PathVariable ("projectId") int projectId,
                                       @PathVariable ("username") String username,
                                       @PathVariable ("start") String start,
                                       @PathVariable ("end") String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return projectService.getAllScores(projectId, username, startDate, endDate);
    }


    @PostMapping("/setstartdate")
    public void setStartDate(@RequestBody Map<String, String> requestBody) {
        if(requestBody.get("starttime") != null) {
            startDate = requestBody.get("starttime");
        }
    }

    @PostMapping("/setenddate")
    public void setEndDate(@RequestBody Map<String, String> requestBody) {
        if(requestBody.get("endtime") != null) {
            endDate = requestBody.get("endtime");
        }
    }

    @GetMapping("/getstartdate")
    public List<String> getStartDate() throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date result= df1.parse(startDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result);

        String month = Integer.toString((cal.get(Calendar.MONTH)+1));
        String day = Integer.toString(cal.get(Calendar.DATE));

        if(month.length() < 2){
            month = "0" + Integer.toString((cal.get(Calendar.MONTH)+1));
        }
        if(day.length() < 2){
            day = "0" + Integer.toString(cal.get(Calendar.DATE));
        }

        List<String> date = new ArrayList<>();
        date.add(cal.get(Calendar.YEAR) + "-" + month+ "-" + day);
        return date;
    }

    @GetMapping("/getenddate")
    public List<String> getEndDate() throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date result = df1.parse(endDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result);

        String month = Integer.toString((cal.get(Calendar.MONTH)+1));
        String day = Integer.toString(cal.get(Calendar.DATE));

        if(month.length() < 2){
            month = "0" + Integer.toString((cal.get(Calendar.MONTH)+1));
        }
        if(day.length() < 2){
            day = "0" + Integer.toString(cal.get(Calendar.DATE));
        }

        List<String> date = new ArrayList<>();
        date.add(cal.get(Calendar.YEAR) + "-" + month+ "-" + day);
        return date;
    }

}

