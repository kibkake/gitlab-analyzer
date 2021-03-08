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

    public enum UseWhichDevField {EITHER, NAME, USERNAME};

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

    public int getNumDevCommits(int projectId, String username, LocalDate start, LocalDate end,
                                UseWhichDevField devField) {
        List<Commit> allCommits = this.getDevCommits(projectId, username, start, end, devField);
        return allCommits.size();
    }

    public int getNumDevMergeRequests(int projectId, String username, LocalDate start, LocalDate end) {
        List<MergeRequest> devMRs = getDevMergeRequests(projectId, username, start, end);
        return devMRs.size();
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

    public List<DateScore> getDevCommitScoresPerDay(int projectId, String username, LocalDate start,
                                                    LocalDate end, UseWhichDevField devField) {
        List<Commit> allDevCommits = this.getDevCommits(projectId, username, start, end, devField);
        HashMap<String, DateScore> dateMap = new HashMap<String, DateScore>();

        for (Commit currentCommit: allDevCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        username, currentCommit.getId());
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

    public double getTotalDevCommitScore(int projectId, String username, LocalDate start,
                                         LocalDate end, UseWhichDevField devField) {
        List<DateScore> individualDateScores = this.getDevCommitScoresPerDay(projectId, username,
                                                                                start, end, devField);
        double totalCommitScore = 0;
        for (DateScore currentDateScore : individualDateScores) {
            totalCommitScore += currentDateScore.getCommitScore();
        }
        return totalCommitScore;
    }

    public List<DateScore> getScoresPerDayForMRsAndCommits(int projectId, String username, LocalDate start,
                                                           LocalDate end, UseWhichDevField devFieldForGettingCommits) {
        List<MergeRequest> devMergeRequest = this.getDevMergeRequests(projectId, username, start, end);
        HashMap<String, DateScore> dateMap = new HashMap<String, DateScore>();

        for (MergeRequest mergeRequest : devMergeRequest) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());

            if (!dateMap.containsKey(mergedDate.toString())) {
                DateScore dateScore = new DateScore(mergedDate, mergeRequest.getMrScore(),
                        username, 1, mergeRequest.getId());
                dateMap.put(mergedDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(mergedDate.toString());
                dateScore.addToMergeRequestScore(mergeRequest.getMrScore());
                dateScore.incrementNumMergeRequests();
                dateScore.addMergeRequestIds(mergeRequest.getId());
            }
        }
        System.out.println(dateMap);
        List<Commit> allDevCommits = this.getDevCommits(projectId, username, start, end, devFieldForGettingCommits);
        for (Commit currentCommit: allDevCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        username, currentCommit.getId());
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

    private Developer findDeveloperWithUsernameField(String username, int projectId) {
        List<Developer> developers = getProjectDevelopers(projectId);
        for (Developer developer: developers) {
                if (developer.getUsername().equals(username)) {
                return developer;
            }
        }
        return null;
    }

    private boolean matchesCommitNameOrAuthor(Commit commit, String nameOrUsername) {
        return commit.getCommitterName().equals(nameOrUsername)
               || commit.getAuthorName().equals(nameOrUsername);
    }


    private boolean didDeveloperAuthorCommit(Commit commit, Developer developer,
                                             UseWhichDevField devField) {
        // For this function, it turns out that .getCommitter_name() and
        // .getAuthor_name() of Commit actually tends to return name, not username.
        // So we will be unable just use username exclusively.
        // In this function, either both username and name will be used,
        // or just one of them. It depends on the enum value of devField.

        if (developer == null) {
            return false;
        }
        else if (devField == UseWhichDevField.EITHER) {
            return (developer.getName() != null
                    && matchesCommitNameOrAuthor(commit, developer.getName()))
                   || (developer.getUsername() != null
                       && matchesCommitNameOrAuthor(commit, developer.getUsername()));
        }
        else if (devField == UseWhichDevField.NAME) {
            return developer.getName() != null
                   && matchesCommitNameOrAuthor(commit, developer.getName());
        }
        else {
            return developer.getUsername() != null
                   && matchesCommitNameOrAuthor(commit, developer.getUsername());
        }
    }

    public List<Commit> getDevCommits(int projectId, String username, LocalDate start, LocalDate end,
                                      UseWhichDevField devField) {
        // For anyone calling this function, the username parameter should be equal
        // to the username field of the dev you're talking about.
        Developer developer = findDeveloperWithUsernameField(username, projectId);
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> devCommits = new ArrayList<Commit>();
        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if (commitDate.compareTo(start) >= 0 && commitDate.compareTo(end) <= 0) {
                if (!StringFunctions.inList(commitIds, currentCommit.getId())
                    && didDeveloperAuthorCommit(currentCommit, developer, devField)) {
                    devCommits.add(currentCommit);
                    commitIds.add(currentCommit.getId());
                }
            }
        }
        return devCommits;
    }

    public List<String> getAllDevCommitsArray(int projectId, String username, LocalDate start,
                                              LocalDate end, UseWhichDevField devField) {
        List<Commit> allDevCommits = getDevCommits(projectId, username, start, end, devField);
        List<String> commitsArray = new ArrayList<String>();
        // Each element of commitsArray will store the number of commits (as a string) on a date.
        for (LocalDate time = start; time.isBefore(end.plusDays(1)); time = time.plusDays(1)) {
            List<Commit> commitsThisDay = new ArrayList<Commit>();
            for (Commit currentCommit : allDevCommits) {
                LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
                if (commitDate.compareTo(time) == 0) {
                    commitsThisDay.add(currentCommit);
                }
            }
            commitsArray.add(Integer.toString(commitsThisDay.size()));
        }
        return commitsArray;
    }

    public List<Commit> getCommitByHash(int projectId, String hash) {
        Project project = projectRepository.findProjectById(projectId);
        List<String> commitIds = new ArrayList<String>(); // Will store the IDs of commits counted
        // towards numTotal Commits. Goal is to prevent counting the same commit multiple times.
        List<Commit> commits = new ArrayList<Commit>();

        List<Commit> projectCommits = project.getCommits();
        for (Commit currentCommit : projectCommits) {
            if (!StringFunctions.inList(commitIds, currentCommit.getId())
                && currentCommit.getId().equals(hash)) {
                commits.add(currentCommit);
                commitIds.add(currentCommit.getId());
            }
        }
        return commits;
    }

    public List<MergeRequest> getDevMergeRequests(int projectId, String username, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        List<MergeRequest> devMergeRequests = new ArrayList<>();
        for (MergeRequest mergeRequest : mergeRequests) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());
            if (mergedDate.compareTo(start) >= 0 && mergedDate.compareTo(end) <= 0) {
                for (Developer dev : mergeRequest.getContributors()) {
                    if (dev.getUsername().equals(username)) {
                        devMergeRequests.add(mergeRequest);
                    }
                }
            }
        }
        return devMergeRequests;
    }

    public List<Issue> getDevIssues(int projectId, String username, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<Issue> issues = project.getIssues();
        List<Issue> devIssues = new ArrayList<>();
        for (Issue issue : issues) {
            LocalDate modifiedDate = null;
            String whenIssueModified = issue.getModified_at();
            if (whenIssueModified != null && whenIssueModified != "") {
                modifiedDate = LocalDate.parse(whenIssueModified);
            }
            // At this point, it's fine if modifiedDate is still null, since the
            // issue may have never been modified.

            // TODO - LocalDate.parse cannot deal with the createdAt String,
            // and throws an exception for user2. For now I'm just going to continue to the
            // next iteration of the for loop if the String isn't in proper form.
            // From my tests, it seems like sometimes createdAt is neither null nor
            // an empty string, but instead some other bad string that parse dislikes.

            LocalDate createdAt;
            try {
                createdAt = LocalDate.parse(issue.getCreated_at());
            }
            catch (Exception e) {
                continue;
            }
            if ((modifiedDate == null
                 || (modifiedDate.compareTo(start) >= 0 && modifiedDate.compareTo(end) <= 0))
                && createdAt.compareTo(start) >= 0 && createdAt.compareTo(end) <= 0) {
                List<Note> notes = issue.getNotes();
                for (Note note: notes)  {
                    if (note.getAuthor().getUsername().equals(username)) {
                        devIssues.add(issue);
                    }
                }
            }
        }
        return devIssues;
    }

    public List<Note> getTopDevNotes(int projectID, String username, LocalDate start, LocalDate end,
                                      int limit, boolean applyLimit) {
        List<Note> devNotes = getDevNotes(projectID, username, start, end);
        devNotes.sort(Comparator.comparingInt(Note::getWordCount));
        Collections.reverse(devNotes);
        List<Note> topNotes;
        if (applyLimit) {
            topNotes = devNotes.stream().limit(limit).collect(Collectors.toList());
        }
        else {
            topNotes = devNotes.stream().collect(Collectors.toList());
        }
        return topNotes;
    }

    public List<Note> getDevNotes(int projectId, String username, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<Issue> issues = project.getIssues();
        List<Note> devNotes = new ArrayList<>();
        for (Issue issue : issues) {
            List<Note> issueNotes = issue.getNotes();
            if (issueNotes != null) {
                for (Note note : issueNotes) {
                    LocalDate createdDate = LocalDateFunctions.convertDateToLocalDate(note.getCreatedDate());
                    if (createdDate.compareTo(start) >= 0 && createdDate.compareTo(end) <= 0
                        && didDeveloperWriteNote(note, username)) {
                        devNotes.add(note);
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
                    if (createdDate.compareTo(start) >= 0 && createdDate.compareTo(end) <= 0
                        && didDeveloperWriteNote(note, username)) {
                        devNotes.add(note);
                    }
                }
            }
        }
        return devNotes;
    }

    private boolean didDeveloperWriteNote(Note note, String username) {
        Developer dev = note.getAuthor();
        return dev != null && dev.getUsername() != null && dev.getUsername().equals(username);
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

    public double getTotalDevMRScore(int projectId, String username,
                                       LocalDate start, LocalDate end) {
        double totalMRScore = 0.0;
        List<MergeRequest> devMRs = this.getDevMergeRequests(projectId, username, start, end);
        for (MergeRequest currentMR: devMRs) {
            totalMRScore += currentMR.getMrScore();
        }
        return totalMRScore;
    }

    public int getTotalDevCommentWordCount(int projectId, String username,
                                            LocalDate start, LocalDate end) {
        List<Note> devNotes = this.getTopDevNotes(projectId, username, start, end, 100000, false);
        int totalCommentWordCount = 0;
        for (Note currentNote: devNotes) {
            totalCommentWordCount += currentNote.getWordCount();
        }
        return totalCommentWordCount;
    }

    public AllScores getAllScores(int projectId, String username, LocalDate startDate,
                                  LocalDate endDate, UseWhichDevField devFieldToUseForGettingCommits) {
        AllScores allScores = new AllScores(startDate, endDate, 0, 0, 0);
        double totalCommitScore = this.getTotalDevCommitScore(projectId, username, startDate,
                                                              endDate, devFieldToUseForGettingCommits);
        allScores.setTotalCommitScore(totalCommitScore);
        double totalMergeRequestScore = this.getTotalDevMRScore(projectId, username,
                                                                 startDate, endDate);
        allScores.setTotalMergeRequestScore(totalMergeRequestScore);
        int totalCommentWordCount = this.getTotalDevCommentWordCount(projectId, username, startDate,
                                                                      endDate);
        allScores.setTotalCommentWordCount(totalCommentWordCount);

        return allScores;
    }

    public List<Developer> getMembers(int ProjectId){
        Project project = projectRepository.findProjectById(ProjectId);
        return project.getDevelopers();
    }

}
