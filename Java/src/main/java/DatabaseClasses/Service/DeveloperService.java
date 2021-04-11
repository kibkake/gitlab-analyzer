package main.java.DatabaseClasses.Service;

import main.java.Collections.Commit;
import main.java.Collections.Project;
import main.java.ConnectToGitlab.DeveloperConnection;
import main.java.DatabaseClasses.Repository.Project.ProjectRepository;
import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.Developer.DeveloperRepository;
import main.java.Collections.Developer;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final ProjectRepository projectRepository;


    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, ProjectRepository projectRepository) {
        this.developerRepository = developerRepository;
        this.projectRepository = projectRepository;
    }

    public List<String> getProjectDevUsernames(int projectId) {
        List<Developer> members = developerRepository.findDevelopersByProjectId(projectId);
        List<String> memberUsernames = new ArrayList<>();

        for (int i = 0; i < members.size(); i++){
            memberUsernames.add(members.get(i).getUsername());
        }
        java.util.Collections.sort(memberUsernames);
        return memberUsernames;
    }

    public List<MergeRequest> getDevMergeRequestsAndCommits(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getMergeRequestsAndCommits();
    }

    public List<MergeRequestDateScore> getDevMergeRequestsScores(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getMergeRequestDateScores();
    }

    public List<CommitDateScore> getDevCommitScores(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getCommitDateScores();
    }

    public List<Commit> getDevCommits(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getCommits();
    }

    public void saveDevs(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        project.setDevelopers(new DeveloperConnection().getProjectDevelopersFromGitLab(projectId));
        projectRepository.save(project);
    }
}
