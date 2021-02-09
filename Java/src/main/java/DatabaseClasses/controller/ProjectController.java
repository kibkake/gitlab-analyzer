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

@CrossOrigin
@RestController
@RequestMapping("git/")
public class ProjectController {

    @Autowired
    private static ProjectRepository projectRepository;

    public ProjectController (ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("projects")
    @Query(fields="{'name' : 1, 'created_at' : 1}")
    public List<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("projects/{projectId}")
    @Query(fields="{'memberList' : 1}")
    public List<Projects> getAllMembersOfSingleProject(@RequestParam int projectId) {
        return projectRepository.findByIdContaining(projectId);
    }

//    @GetMapping("projects/{projectId}/{memberList.memberName}")
//    @Query(fields="{'scoreOnMergeRequest' :1, 'scoreCommit' :1 }")
//    public List<Member> getMemberStatsOnCode(@RequestParam String memberName) {
//        return projectsRepository.findMemberByMemberNameIs(memberName);
//    }
}