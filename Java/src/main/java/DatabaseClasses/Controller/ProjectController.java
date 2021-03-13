package main.java.DatabaseClasses.Controller;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Model.AllScores;
import main.java.Model.*;
import main.java.ConnectToGitlab.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
 * Note that when "committerName" is used as a param name in a function, it refers to that developer's username.
 */
@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class ProjectController {

    /*
    TODO - For functions that get commits, they pass in an enum value
    to projectService saying whether to use the dev's name or username
    field (or both). Right now it's hardcoded to go for the both option (EITHER),
    but if it's variable then add the option in the get mapping. E.g., if sometimes
    a dev's commits will have committer_name/author_name equal to their
    username, and other times (or for other devs) it's equal to their name,
    then we have to choose which to use each time. Or just use the EITHER option
    all the time, but this could cause issues if one dev's username overlaps with
    another dev's name.
    */

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
    public List<Issue> getDevIssues(@PathVariable("projectId") int projectId, @PathVariable String end,
                                    @PathVariable String start, @PathVariable String userName) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getDevIssues(projectId, userName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/mergeRequests")
    public List<MergeRequest> getProjectMergeRequests(@PathVariable("projectId") int projectId) {
        return projectService.getProjectMRs(projectId);
    }

    // TODO: Change the mapping from userName to username, to make semantics clearer.
    // It refers to the dev's username, not a user's name.
    @GetMapping("projects/{projectId}/mergeRequests/{userName}/{start}/{end}")
    public List<MergeRequest> getDevMergeRequests(@PathVariable("projectId") int projectId,
                                                  @PathVariable("userName") String userName,
                                                  @PathVariable("start") String start,
                                                  @PathVariable("end") String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getDevMergeRequests(projectId, userName, StartLocalTime, endLocalTime);
    }

    private ProjectService.UseWhichDevField whichDevFieldIsString(String whichDevField) {
        if (whichDevField.equalsIgnoreCase("username")) {
            return ProjectService.UseWhichDevField.USERNAME;
        }
        else if (whichDevField.equalsIgnoreCase("name")) {
            return ProjectService.UseWhichDevField.NAME;
        }
        else {
            return ProjectService.UseWhichDevField.EITHER;
        }
    }

    @GetMapping("projects/{projectId}/MRsAndCommitScoresPerDay/{committerName}/{start}/{end}/{whichDevField}")
    public List<DateScore> getDevMRsAndCommitScorePerDay(@PathVariable("projectId") int projectId,
                                                         @PathVariable("committerName") String committerName,
                                                         @PathVariable("start") String start,
                                                         @PathVariable("end")String end,
                                                         @PathVariable("whichDevField")String whichDevField) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getScoresPerDayForMRsAndCommits(projectId, committerName, StartLocalTime, endLocalTime,
                whichDevFieldIsString(whichDevField));
    }

    @GetMapping("projects/{projectId}/Commits/{committerName}/{start}/{end}/{whichDevField}")
    public List<Commit> getAllDevCommits(@PathVariable("projectId") int projectId,
                                         @PathVariable("committerName") String committerName,
                                         @PathVariable("start") String start,
                                         @PathVariable("end")String end,
                                         @PathVariable("whichDevField")String whichDevField) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getDevCommits(projectId, committerName, StartLocalTime, endLocalTime,
                whichDevFieldIsString(whichDevField));
    }

    @GetMapping("projects/{projectId}/Commitsarray/{committerName}/{start}/{end}/{whichDevField}")
    public List<String> getCommitsArray(@PathVariable("projectId") int projectId,
                                        @PathVariable("committerName") String committerName,
                                        @PathVariable("start") String start,
                                        @PathVariable("end")String end,
                                        @PathVariable("whichDevField")String whichDevField) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        List<String> commitsArray = new ArrayList<>();
        commitsArray = projectService.getAllDevCommitsArray(projectId, committerName, StartLocalTime, endLocalTime,
                whichDevFieldIsString(whichDevField));
        return commitsArray;
    }

    @GetMapping("projects/{projectId}/Commit/{hash}")
    public List<Commit> getACommit(@PathVariable("projectId") int projectId,
                                   @PathVariable("hash") String hash) {

        return projectService.getCommitByHash(projectId, hash);
    }

    @GetMapping("projects/{projectId}/commitScoresPerDay/{committerName}/{start}/{end}/{whichDevField}")
    public List<DateScore> getDevCommitScoresWithDates(@PathVariable("projectId") int projectId,
                                                       @PathVariable("committerName") String committerName,
                                                       @PathVariable("start") String start,
                                                       @PathVariable("end")String end,
                                                       @PathVariable("whichDevField")String whichDevField) {

        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getDevCommitScoresPerDay(projectId, committerName, StartLocalTime, endLocalTime,
                whichDevFieldIsString(whichDevField));
    }

    // TODO: should be checked, doesn't return data.
    // TODO: Change "topTenUserNotes" to "topTenDevNotes".
    @GetMapping("projects/{projectId}/topTenUserNotes/{committerName}/{start}/{end}")
    public List<Note> getTopTenDevNotes(@PathVariable("projectId") int projectId,
                                        @PathVariable("committerName") String committerName,
                                        @PathVariable("start") String start,
                                        @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getTopDevNotes(projectId, committerName, StartLocalTime, endLocalTime, 10, true);
    }

    // TODO: Change "allUserNotes" to "allDevNotes".
    @GetMapping("projects/{projectId}/allUserNotes/{committerName}/{start}/{end}")
    public List<Note> getAllDevNotes(@PathVariable("projectId") int projectId,
                                     @PathVariable("committerName") String committerName,
                                     @PathVariable("start") String start,
                                     @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        return projectService.getDevNotes(projectId, committerName, StartLocalTime, endLocalTime);
    }

    @GetMapping("projects/{projectId}/totalCommitScore/{committerName}/{start}/{end}/{whichDevField}")
    public double totalCommitScore(@PathVariable("projectId") int projectId,
                                   @PathVariable("committerName") String committerName,
                                   @PathVariable("start") String start,
                                   @PathVariable("end") String end,
                                   @PathVariable("whichDevField") String whichDevField) {
        LocalDate startLocalDate = LocalDate.parse(start);
        LocalDate endLocalDate = LocalDate.parse(end);
        return projectService.getTotalDevCommitScore(projectId, committerName, startLocalDate,
                endLocalDate, whichDevFieldIsString(whichDevField));
    }

    @GetMapping("projects/{projectId}/commit/{commitId}")
    public Commit getCommit(@PathVariable String commitId, @PathVariable int projectId) {
        return projectService.getCommit(projectId, commitId);
    }

    @GetMapping("projects/{projectId}/mergeRequest/{mrId}")
    public MergeRequest getMergeRequest(@PathVariable int mrId, @PathVariable int projectId) {
        return projectService.getMergeRequest(projectId, mrId);
    }

    @GetMapping("projects/{projectId}/allTotalScores/{username}/{start}/{end}/{whichDevField}")
    public AllScores allTotalScores(@PathVariable ("projectId") int projectId,
                                    @PathVariable ("username") String username,
                                    @PathVariable ("start") String start,
                                    @PathVariable ("end") String end,
                                    @PathVariable ("whichDevField") String whichDevField) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return projectService.getAllScores(projectId, username, startDate, endDate,
                whichDevFieldIsString(whichDevField));
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

    @PostMapping("/testnames")
    public void setEndDate(@RequestBody List<String> requestBody) {
        System.out.println(requestBody);
    }

    @GetMapping("/getusernames/{projectId}")
    public List<String> getMemberUsernames(@PathVariable("projectId") int projectId) {

        Project project = projectService.getProject(projectId);
        if (!project.isInfoSet()) {
            projectService.setProjectInfo(projectId);
        }

        List<Developer> members = projectService.getProjectDevelopers(projectId);
        List<String> memberUsernames = new ArrayList<>();

        for (int i = 0; i < members.size(); i++){
            memberUsernames.add(members.get(i).getUsername());
        }
        java.util.Collections.sort(memberUsernames);
        return memberUsernames;
    }

    @GetMapping("projects/{projectId}/allUserNotesForChart/{committerName}/{start}/{end}")
    public List<Note> getAllDevNotesForChart(@PathVariable("projectId") int projectId,
                                     @PathVariable("committerName") String committerName,
                                     @PathVariable("start") String start,
                                     @PathVariable("end")String end) {
        LocalDate StartLocalTime = LocalDate.parse(start);
        LocalDate endLocalTime = LocalDate.parse(end);
        try {
            return projectService.getDevNotesForChart(projectId, committerName, StartLocalTime, endLocalTime);
        }catch(ParseException exception){
            System.out.println(exception.getMessage());
            List<Note> notes = new ArrayList<>();
            return notes;
        }
    }
}

