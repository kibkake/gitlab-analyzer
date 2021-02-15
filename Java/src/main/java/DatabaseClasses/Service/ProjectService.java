package main.java.DatabaseClasses.Service;

import main.java.Model.Commit;
import main.java.ConnectToGitlab.CommitConnection;
import main.java.Model.Developer;
import main.java.ConnectToGitlab.DeveloperConnection;
import main.java.Model.Issue;
import main.java.ConnectToGitlab.IssueConnection;
import main.java.Model.MergeRequest;
import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.Model.Project;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import main.java.Functions.LocalDateFunctions;
import main.java.Functions.StringFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<CommitDateScore> getUserCommitScoresPerDay(int projectId, String committerName,
                                                           LocalDate start, LocalDate end) {
        List<Commit> allUserCommits = this.getAllUserCommits(projectId, committerName, start, end);
        HashMap<String, CommitDateScore> dateMap = new HashMap<String, CommitDateScore>();

        for (Commit currentCommit: allUserCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                CommitDateScore commitDateScore = new CommitDateScore(commitDate, currentCommit.getCommitScore(),
                        committerName, currentCommit.getId());
                dateMap.put(commitDate.toString(), commitDateScore);
            } else {
                CommitDateScore commitDateScore = dateMap.get(commitDate.toString());

                commitDateScore.addToScore(currentCommit.getCommitScore());
                commitDateScore.incrementNumberOfCommitsBy1();
                commitDateScore.addCommitIds(currentCommit.getId());
            }
        }
        List<CommitDateScore> commitDateScores = new ArrayList<CommitDateScore>(dateMap.values());
        System.out.println(commitDateScores);
        return commitDateScores;
    }

    public double getTotalUserCommitScore(int projectId, String committerName,
                                       LocalDate start, LocalDate end) {
        List<CommitDateScore> individualCommitScores = this.getUserCommitScoresPerDay(projectId, committerName,
                                                                                start, end);
        double totalCommitScore = 0;
        for (CommitDateScore currentCommitDateScore : individualCommitScores) {
            totalCommitScore += currentCommitDateScore.getScore();
        }
        return totalCommitScore;
    }

    public List<Commit> getAllUserCommits(int projectId, String committerName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> userCommits = new ArrayList<Commit>();

        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if (commitDate.compareTo(start) >= 0 && commitDate.compareTo(end) <= 0) {
                if (!StringFunctions.inList(commitIds, currentCommit.getId()) &&
                        currentCommit.getCommitter_name().equals(committerName)) {
                    userCommits.add(currentCommit);
                    commitIds.add(currentCommit.getId());
                }
            }
        }
        return userCommits;
    }

    public List<MergeRequestDateScore> getUsersMergeRequestScorePerDay(int projectId, String committerName,
                                                                       LocalDate start, LocalDate end) {
        List<MergeRequest> userMergeRequest = this.getUserMergeRequests(projectId, committerName);
        HashMap<String, MergeRequestDateScore> dateMap = new HashMap<String, MergeRequestDateScore>();

        for (MergeRequest mergeRequest : userMergeRequest) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());

            List<String> commitIds = new ArrayList<>();
            for(Commit commit: mergeRequest.getCommits()) {
                commitIds.add(commit.getId());
            }

            if (mergedDate.compareTo(start) >= 0 && mergedDate.compareTo(end) <= 0) {
                if (!dateMap.containsKey(mergedDate.toString())) {
                    MergeRequestDateScore MergeRequestDateScore = new MergeRequestDateScore(mergedDate, mergeRequest.getScore(),
                            committerName, 1,mergeRequest.getId(), commitIds);
                    dateMap.put(mergedDate.toString(), MergeRequestDateScore);
                } else {
                    MergeRequestDateScore MergeRequestDateScore = dateMap.get(mergedDate.toString());

                    MergeRequestDateScore.addToScore(mergeRequest.getScore());
                    MergeRequestDateScore.incrementNumMergeRequests();
                    MergeRequestDateScore.addCommitIds(commitIds);
                    MergeRequestDateScore.addMergeRequestIds(mergeRequest.getId());
                }
            }
        }
        List<MergeRequestDateScore> MergeRequestDateScores = new ArrayList<MergeRequestDateScore>(dateMap.values());
        return MergeRequestDateScores;
    }



    public List<MergeRequest> getUserMergeRequests(int projectId, String committerName) {
        System.out.println("MergeRequests");
        Project project = projectRepository.findProjectById(projectId);
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        List<MergeRequest> userMergeRequests = new ArrayList<>();
        for (MergeRequest mergeRequest : mergeRequests) {
            for (Developer dev: mergeRequest.getContributors()) {
                if(dev.getName().equals(committerName)) {
                    userMergeRequests.add(mergeRequest);
                }
            }
        }
            return mergeRequests;
    }
}
