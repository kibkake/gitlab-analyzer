package main.java.DatabaseClasses.Controller;

import main.java.ConnectToGitlab.Project.Project;
import main.java.ConnectToGitlab.Project.ProjectConnection;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
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

    @Autowired
    private static ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    // Just a couple of examples to show how querying works, should be changed

    // Example of getting projects only with field of name
    @GetMapping("projects")
    public List<Project> getAllProjects() {
        return ProjectConnection.getAllProjects();

    }


    // Example of getting memberList by project id,
    // But the current json object structure does not have the memberList inside Project structure
    // so consider this just as an example pseudocode
    @GetMapping("projects/{projectId}")
    @Query(value="{'project_ID': ?0}", fields="{'memberList' : 1, 'project_ID': 0}")
    public List<Project> getAllMembersOfSingleProject(@RequestParam int projectId) {
        return projectRepository.findByIdContaining(projectId);
    }

}

