package main.java.DatabaseClasses.Service;

import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Model.AllScores;
import main.java.Model.*;
import main.java.ConnectToGitlab.CommitConnection;
import main.java.ConnectToGitlab.DeveloperConnection;
import main.java.ConnectToGitlab.IssueConnection;
import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import main.java.Functions.LocalDateFunctions;
import main.java.Functions.StringFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllBy();
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

    public int getNumUserCommits(int projectId, String committerName, LocalDate start, LocalDate end ) {
        List<Commit> allCommits = this.getUserCommits(projectId, committerName, start, end);
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

        project.setDevelopers(new DeveloperConnection().getProjectDevelopersFromGitLab(projectId));
        project.setCommits(new CommitConnection().getProjectCommitsFromGitLab(projectId));
        project.setMergedRequests(new MergeRequestConnection().getProjectMergeRequestsFromGitLab(projectId));
        project.setIssues(new IssueConnection().getProjectIssuesFromGitLab(projectId));
        project.setInfoSet(true);
        projectRepository.save(project);
    }

    public List<DateScore> getUserCommitScoresPerDay(int projectId, String committerName,
                                                     LocalDate start, LocalDate end) {
        List<Commit> allUserCommits = this.getUserCommits(projectId, committerName, start, end);
        HashMap<String, DateScore> dateMap = new HashMap<String, DateScore>();

        for (Commit currentCommit: allUserCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        committerName, currentCommit.getId());
                dateMap.put(commitDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(commitDate.toString());

                dateScore.addToCommitScore(currentCommit.getCommitScore());
                dateScore.incrementNumberOfCommitsBy1();
                dateScore.addCommitIds(currentCommit.getId());
            }
        }
        List<DateScore> dateScores = new ArrayList<DateScore>(dateMap.values());
        return dateScores;
    }

    public double getTotalUserCommitScore(int projectId, String committerName,
                                       LocalDate start, LocalDate end) {
        List<DateScore> individualDateScores = this.getUserCommitScoresPerDay(projectId, committerName,
                                                                                start, end);
        double totalCommitScore = 0;
        for (DateScore currentDateScore : individualDateScores) {
            totalCommitScore += currentDateScore.getCommitScore();
        }
        return totalCommitScore;
    }


    public List<DateScore> getScoresPerDayForMRsAndCommits(int projectId, String committerName,
                                                           LocalDate start, LocalDate end) {
        List<MergeRequest> userMergeRequest = this.getUserMergeRequests(projectId, committerName, start, end);
        HashMap<String, DateScore> dateMap = new HashMap<String, DateScore>();

        for (MergeRequest mergeRequest : userMergeRequest) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());

            if (!dateMap.containsKey(mergedDate.toString())) {
                DateScore dateScore = new DateScore(mergedDate, mergeRequest.getMrScore(),
                        committerName, 1, mergeRequest.getId());
                dateMap.put(mergedDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(mergedDate.toString());
                dateScore.addToMergeRequestScore(mergeRequest.getMrScore());
                dateScore.incrementNumMergeRequests();
                dateScore.addMergeRequestIds(mergeRequest.getId());
            }
        }
        System.out.println(dateMap);
        List<Commit> allUserCommits = this.getUserCommits(projectId, committerName, start, end);
        for (Commit currentCommit: allUserCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        committerName, currentCommit.getId());
                dateMap.put(commitDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(commitDate.toString());

                dateScore.addToCommitScore(currentCommit.getCommitScore());
                dateScore.incrementNumberOfCommitsBy1();
                dateScore.addCommitIds(currentCommit.getId());
            }
        }
        List<DateScore> dateScores = new ArrayList<DateScore>(dateMap.values());
        System.out.println(dateScores);
        return dateScores;
    }


    public List<Commit> getUserCommits(int projectId, String committerName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> userCommits = new ArrayList<Commit>();

        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if (commitDate.compareTo(start) >= 0 && commitDate.compareTo(end) <= 0) {
                if (!StringFunctions.inList(commitIds, currentCommit.getId()) &&
                        (currentCommit.getCommitter_name().equals(committerName)) || currentCommit.getAuthor_name().equals(committerName)) {
                    userCommits.add(currentCommit);
                    commitIds.add(currentCommit.getId());
                }
            }
        }
        return userCommits;
    }

    public List<String> getAllUserCommitsArray(int projectId, String committerName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>();
        List<Commit> projectCommits = project.getCommits();
        List<String> commitsArray = new ArrayList<>();

        for (LocalDate time = start; time.isBefore(end.plusDays(1)); time = time.plusDays(1)){
            List<Commit> commits = new ArrayList<>();

            for (Commit currentCommit : projectCommits) {
                LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
                if (commitDate.compareTo(time) == 0) {
                    if (!StringFunctions.inList(commitIds, currentCommit.getId()) &&
                            currentCommit.getCommitter_name().equals(committerName)) {
                        commits.add(currentCommit);
                        commitIds.add(currentCommit.getId());
                    }
                }
            }
            commitsArray.add(Integer.toString(commits.size()));
        }
        return commitsArray;
    }

    public List<Commit> getCommitByHash(int projectId, String hash) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> userCommits = new ArrayList<Commit>();

        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if (!StringFunctions.inList(commitIds, currentCommit.getId()) &&
                    currentCommit.getId().equals(hash)) {
                userCommits.add(currentCommit);
                commitIds.add(currentCommit.getId());
            }
        }
        return userCommits;
    }

    public List<MergeRequest> getUserMergeRequests(int projectId, String committerName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        List<MergeRequest> userMergeRequests = new ArrayList<>();
        for (MergeRequest mergeRequest : mergeRequests) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());
            if (mergedDate.compareTo(start) >= 0 && mergedDate.compareTo(end) <= 0) {
                for (Developer dev : mergeRequest.getContributors()) {
                    if (dev.getName().equals(committerName) || dev.getUsername().equals(committerName)) {
                        userMergeRequests.add(mergeRequest);
                    }
                }
            }
        }
        return userMergeRequests;
    }

    public List<Issue> getUserIssues(int projectId, String userName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<Issue> issues = project.getIssues();
        List<Issue> userIssues = new ArrayList<>();
        for (Issue issue : issues) {
            LocalDate modifiedDate = LocalDate.parse(issue.getModified_at());
            LocalDate createdAt = LocalDate.parse(issue.getCreated_at());
            if (modifiedDate.compareTo(start) >= 0 && modifiedDate.compareTo(end) <= 0 &&
                    createdAt.compareTo(start) >= 0 && createdAt.compareTo(end) <= 0) {
                List<Note> notes = issue.getNotes();
                for (Note note: notes)  {
                    if (note.getUsername().equals(userName) || note.getAuthor().equals(userName)) {
                        userIssues.add(issue);
                    }
                }
            }
        }
        return userIssues;
    }

    public List<Note> getTopUserNotes(int projectID, String userName, LocalDate start, LocalDate end,
                                      int limit, boolean applyLimit) {
        List<Note> userNotes = getUserNotes(projectID, userName, start, end);
        userNotes.sort(Comparator.comparingInt(Note::getWordCount));
        Collections.reverse(userNotes);
        List<Note> topNotes;
        if (applyLimit) {
            topNotes = userNotes.stream().limit(limit).collect(Collectors.toList());
        }
        else {
            topNotes = userNotes.stream().collect(Collectors.toList());
        }
        return topNotes;
    }

    public List<Note> getUserNotes(int projectId, String userName, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<Issue> issues = project.getIssues();
        List<Note> userNotes = new ArrayList<>();
        for (Issue issue : issues) {
            List<Note> issueNotes = issue.getNotes();
            if (issueNotes != null) {
                for (Note note : issueNotes) {
                    LocalDate createdDate = LocalDateFunctions.convertDateToLocalDate(note.getCreatedDate());
                    if (createdDate.compareTo(start) >= 0 && createdDate.compareTo(end) <= 0 &&
                        note != null && note.getUsername() != null &&
                        note.getUsername().equals(userName)) {
                        userNotes.add(note);
                    }
                }
            }
        }
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        for (MergeRequest mergeRequest : mergeRequests) {
            List<Note> mrNotes = mergeRequest.getNotes();
            if (mrNotes != null) {
                for (Note note : mrNotes) {
                    LocalDate createdDate = LocalDateFunctions.convertDateToLocalDate(note.getCreatedDate());
                    if (createdDate.compareTo(start) >= 0 && createdDate.compareTo(end) <= 0 &&
                        note != null && note.getUsername() != null &&
                        note.getUsername().equals(userName)) {
                        userNotes.add(note);
                    }
                }
            }
        }
        return userNotes;
    }

    public Commit getCommit(int projectId, String commitId) {
        Project project = projectRepository.findProjectById(projectId);
        List<Commit> commits = project.getCommits();
        Commit commit = commits.stream()
        .filter(c -> commitId.equals(c.getId()))
                .findAny()
                .orElse(null);
        return commit;
    }

    public MergeRequest getMergeRequest(int projectId, int commitId) {
        Project project = projectRepository.findProjectById(projectId);
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        MergeRequest mergeRequest = mergeRequests.stream()
                .filter(mr -> commitId == mr.getId())
                .findAny()
                .orElse(null);
        return mergeRequest;
    }

    public double getTotalUserMRScore(int projectId, String username,
                                       LocalDate start, LocalDate end) {
        double totalMRScore = 0.0;
        List<MergeRequest> userMRs = this.getUserMergeRequests(projectId, username, start, end);
        for (MergeRequest currentMR: userMRs) {
            totalMRScore += currentMR.getMrScore();
        }
        return totalMRScore;
    }

    public int getTotalUserCommentWordCount(int projectId, String username,
                                            LocalDate start, LocalDate end) {
        List<Note> userNotes = this.getTopUserNotes(projectId, username, start, end, 100000, false);
        int totalCommentWordCount = 0;
        for (Note currentNote: userNotes) {
            totalCommentWordCount += currentNote.getWordCount();
        }
        return totalCommentWordCount;
    }

    public AllScores getAllScores(int projectId, String username,
                                  LocalDate startDate, LocalDate endDate) {
        AllScores allScores = new AllScores(startDate, endDate, 0, 0, 0);
        double totalCommitScore = this.getTotalUserCommitScore(projectId, username,
                                                               startDate, endDate);
        allScores.setTotalCommitScore(totalCommitScore);
        double totalMergeRequestScore = this.getTotalUserMRScore(projectId, username,
                                                                 startDate, endDate);
        allScores.setTotalMergeRequestScore(totalMergeRequestScore);
        int totalCommentWordCount = this.getTotalUserCommentWordCount(projectId, username, startDate,
                                                                      endDate);
        allScores.setTotalCommentWordCount(totalCommentWordCount);

        return allScores;
    }

}
