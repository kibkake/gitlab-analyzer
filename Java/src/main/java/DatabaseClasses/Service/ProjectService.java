package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Commit.CommitConnection;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperConnection;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.Issue.IssueConnection;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.ConnectToGitlab.MergeRequests.MergeRequestConnection;
import main.java.ConnectToGitlab.User;
import main.java.DatabaseClasses.Model.Project;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(int projectId) {
        return projectRepository.findProjectById(projectId);
    }

    public void saveNewProjects(List<Project> projects) {
        projectRepository.saveAll(projects);
    }

    public List<Developer> getProjectDevelopers(int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        return project.getDevelopers();
    }

    public List<Issue> getProjectIssues(int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        return project.getIssues();
    }

    public String getProjectDescription(int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        return project.getDescription();
    }

    @Transactional
    public void setProjectInfo(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        project.setDevelopers(DeveloperConnection.getProjectDevelopers(projectId));
        project.setCommits(CommitConnection.getProjectCommits(projectId));
        project.setMergedRequests(MergeRequestConnection.getProjectMergeRequests(projectId));
        project.setIssues(IssueConnection.getProjectIssues(projectId));
        project.setInfoSet(true);
        projectRepository.save(project);
    }


//    public List<Developer> getDevsCommitsAndScores(int projectId, int developerId) {
//        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
//                "Project with id " + projectId + " does not exist"));
//
//        Developer developer = project.getDevelopers().stream().filter(dev -> dev.getId() == developerId).findAny()
//                .orElse(null);
//
//        List<Commit> devsCommits = project.getCommits().stream()
//                .filter(mrs -> mrs.getContributors().stream().anyMatch(devs ->
//                                devs.getId() == this.id))
//                .collect(Collectors.toList());
//
//
//
//    }
//
//}
//
//    public List<Commit> getDevCommits(List<Commit> commits) {
//        List<Commit> filteredCommits = commits.stream()
//                .filter(p -> p.getAuthor_name().equals(this.name)).collect(Collectors.toList());
//        return filteredCommits;
//    }
//
//    public List<MergeRequest> getDevMergeRequests(List<MergeRequest> mergeRequests) {
//        List<MergeRequest> filteredList = mergeRequests.stream()
//                .filter(mrs -> mrs.getContributors().stream()
//                        .anyMatch(devs ->
//                                devs.getId() == this.id))
//                .collect(Collectors.toList());
//        return filteredList;
//    }
}
