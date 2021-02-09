package DatabaseClasses.controller;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("git/")
public class ProjectController {

    @Autowired
    private static ProjectRepository projectsRepository;

    public ProjectController(ProjectRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    // Example of getting projects only with field of name, not tested
    @GetMapping("projects")
    @Query(fields="{'name' : 1}")
    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }

    // Example of getting projects by id, hasn't tested
    @GetMapping("projects/{projectId}")
    @Query(fields="{'memberList' : 1}")
    public List<Projects> getAllMembersOfSingleProject(@RequestParam int projectId) {
        return projectsRepository.findByIdContaining(projectId);
    }

}
