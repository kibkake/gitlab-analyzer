package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Commit.CommitConnection;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Developer.DeveloperConnection;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.Issue.IssueConnection;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.ConnectToGitlab.MergeRequests.MergeRequestConnection;
import main.java.DatabaseClasses.DateScore;
import main.java.DatabaseClasses.Model.Project;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import main.java.Functions.LocalDateFunctions;
import main.java.Functions.StringFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<MergeRequest> getProjectMRs(int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        return project.getMergedRequests();
    }

    public int getNumUserCommits(int projectId, String committerName) {
        List<Commit> allCommits = this.getAllUserCommits(projectId, committerName);
        return allCommits.size();
    }

    public int getNumUserMergeRequests(int projectId, String committerName) {
        int numTotalMRs = 0;
        Project project = projectRepository.findProjectById(projectId);

        List<MergeRequest> projectMRs = project.getMergedRequests();
        for (MergeRequest currentMR: projectMRs) {
            if (currentMR.isAContributor(committerName)) {
                numTotalMRs++;
            }
        }
        return numTotalMRs;
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

    public List<DateScore> getUserCommitScoresPerDay(int projectId, String committerName,
                                                     LocalDate start, LocalDate end) {
        List<Commit> allUserCommits = this.getAllUserCommits(projectId, committerName);
        List<DateScore> returnVar = new ArrayList<DateScore>();

        for (Commit currentCommit: allUserCommits) {
            LocalDate currentDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if (currentDate.compareTo(start) >= 0 && currentDate.compareTo(end) <= 0) {
                DateScore dateScore = new DateScore(currentDate, currentCommit.getCommitScore(),
                                                    committerName);
                returnVar.add(dateScore);
            }
        }

        // Continue here - could go through the list and aggregate the scores for each Date, turning
        // them into one DateScore object for that date.

        return returnVar;
    }

    public int getTotalUserCommitScore(int projectId, String committerName,
                                       LocalDate start, LocalDate end) {
        List<DateScore> individualCommitScores = this.getUserCommitScoresPerDay(projectId, committerName,
                                                                                start, end);
        int totalCommitScore = 0;

        for (DateScore currentDateScore: individualCommitScores) {
            totalCommitScore += currentDateScore.getCommitScore();
        }
        return totalCommitScore;
    }

    public List<Commit> getAllUserCommits(int projectId, String committerName) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> returnVar = new ArrayList<Commit>();

        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            if (!StringFunctions.inList(commitIds, currentCommit.getId()) &&
                    currentCommit.getCommitter_name().equals(committerName)) {
                returnVar.add(currentCommit);
                commitIds.add(currentCommit.getId());
            }
        }
        return returnVar;
    }
    
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
