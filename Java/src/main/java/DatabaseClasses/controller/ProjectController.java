package DatabaseClasses.controller;

import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin //(origins = "http://localhost:3000/")
@RestController
@RequestMapping("git/")
public class ProjectController {

    @Autowired
    private static ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("projects")
    @Query(fields="{}")
    public List<Projects> getAll() {
        return projectRepository.findAll();
    }



    @PostMapping("updateProjects")
    public static void saveProject(@RequestBody Projects project){
        projectRepository.save(project);
    }


}