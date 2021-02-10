package main.java.DatabaseClasses.controller;

import main.java.DatabaseClasses.Model.Projects;
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
@RequestMapping("git/")
public class ProjectsController {

    @Autowired
    private static ProjectRepository projectsRepository;

    public ProjectsController(ProjectRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }


    // Just a couple of examples to show how querying works, should be changed

    // Example of getting projects only with field of name
    @GetMapping("projects")
    @Query(fields="{'project_NAME' : 1, 'project_ID' : 0}")
    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }


    // Example of getting memberList by project id,
    // But the current json object structure does not have the memberList inside Project structure
    // so consider this just as an example pseudocode
    @GetMapping("projects/{projectId}")
    @Query(value="{'project_ID': ?0}", fields="{'memberList' : 1, 'project_ID': 0}")
    public List<Projects> getAllMembersOfSingleProject(@RequestParam int projectId) {
        return projectsRepository.findByIdContaining(projectId);
    }

}
