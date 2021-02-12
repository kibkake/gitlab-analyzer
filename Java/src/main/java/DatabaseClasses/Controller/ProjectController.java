package main.java.DatabaseClasses.Controller;

import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.DatabaseClasses.Model.Project;
import main.java.ConnectToGitlab.Project.ProjectConnection;
import main.java.DatabaseClasses.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//        Project project = projectService.getProject(projectId);
//        if (!project.isInfoSet()) {
            projectService.setProjectInfo(projectId);
//            project = projectService.getProject(projectId); // get project now that it has been modified
//        }
        Project project = projectService.getProject(projectId);
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

    @GetMapping("projects/{committerName}/numCommits")
    public int getNumUserCommits(@PathVariable("committerName") String committerName) {
        return projectService.getNumUserCommits(committerName);
    }

    @GetMapping("projects/{committerName}/numMergeRequests")
    public int getNumUserMergeRequests(@PathVariable("committerName") String committerName) {
        return projectService.getNumUserMergeRequests(committerName);
    }

//    @GetMapping("projects/{projectId}/developers/{developerId}/graph")
//    public List<Developer> getDevsGraphData(@PathVariable("projectId") int projectId, @PathVariable("developerId") int developerId) {
//        return projectService.getDevsCommitsAndScores(projectId, developerId);
//    }

}

