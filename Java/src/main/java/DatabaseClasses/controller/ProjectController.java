package main.java.DatabaseClasses.controller;

import main.java.DatabaseClasses.model.Projects;
import main.java.DatabaseClasses.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController("ProjectController")
//@RequestMapping("git/")
public class ProjectController {

    @Autowired
    private static ProjectRepository projectsRepository;

    public ProjectController(ProjectRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @GetMapping("projects")
    @Query(fields="{'name' : 1, 'created_at' : 1}")
    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }

    @GetMapping("projects/{projectId}")
    @Query(fields="{'memberList' : 1}")
    public List<Projects> getAllMembersOfSingleProject(@RequestParam int projectId) {
        return projectsRepository.findByProjectId(projectId);
    }

}

