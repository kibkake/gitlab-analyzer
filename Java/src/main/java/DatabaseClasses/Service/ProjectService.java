package main.java.DatabaseClasses.Service;

import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Scores.DateScore;
import main.java.DatabaseClasses.Scores.AllScores;
import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.Commit.CommitRepository;
import main.java.DatabaseClasses.Repository.Developer.DeveloperRepository;
import main.java.DatabaseClasses.Repository.MergeRequest.MergeRequestRepository;
import main.java.DatabaseClasses.Repository.Project.ProjectRepository;
import main.java.DatabaseClasses.Repository.Snapshot.SnapshotRepository;
import main.java.Collections.*;
import main.java.ConnectToGitlab.CommitConnection;
import main.java.ConnectToGitlab.DeveloperConnection;
import main.java.ConnectToGitlab.IssueConnection;
import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.Functions.LocalDateFunctions;
import main.java.Functions.StringFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MergeRequestRepository mergeRequestRepository;
    private final CommitRepository commitRepository;
    private final DeveloperRepository developerRepository;
    private final SnapshotRepository snapshotRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MergeRequestRepository mergeRequestRepository,
                          CommitRepository commitRepository, DeveloperRepository developerRepository, SnapshotRepository snapshotRepository) {
        this.projectRepository = projectRepository;
        this.mergeRequestRepository = mergeRequestRepository;
        this.commitRepository = commitRepository;
        this.developerRepository = developerRepository;
        this.snapshotRepository = snapshotRepository;
    }

    public void saveProjectIssues(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        project.setIssues(new IssueConnection().getProjectIssuesFromGitLab(projectId));
    }

    public enum UseWhichDevField {EITHER, NAME, USERNAME};

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

    public int getNumDevMergeRequests(int projectId, String username, LocalDate start, LocalDate end) {
        List<MergeRequest> devMRs = getDevMergeRequests(projectId, username, start, end);
        return devMRs.size();
    }

    @Transactional(timeout = 1200000)
    public void setProjectInfo(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        if (project.projectHasBeenUpdated()) {
            project.setDevelopers(new DeveloperConnection().getProjectDevelopersFromGitLab(projectId));
            project.setCommits(new CommitConnection().getProjectCommitsFromGitLab(projectId));
            project.setMergedRequests(new MergeRequestConnection().getProjectMergeRequestsFromGitLab(projectId));
            project.setIssues(new IssueConnection().getProjectIssuesFromGitLab(projectId));
            project.setSyncInfo();
            project.setLastSyncAt();
            projectRepository.save(project);
        }
    }

    @Transactional(timeout = 1200000)
    public void setListOfProjectsDevs(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        project.setDevelopers(new DeveloperConnection().getProjectDevelopersFromGitLab(projectId));
        projectRepository.save(project);

    }


    @Transactional(timeout = 1200000) // 20 min
    public void setProjectInfoWithSettings(int projectId, ProjectSettings projectSettings) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));

        project.setDevelopers(new DeveloperConnection().getProjectDevelopersFromGitLab(projectId));
        List<Commit> projectCommits = new CommitConnection().getProjectCommitsFromGitLab(projectId);
        List<MergeRequest> projectMergeRequests = new MergeRequestConnection().getProjectMergeRequestsFromGitLab(projectId);
        project.setIssues(new IssueConnection().getProjectIssuesFromGitLab(projectId));
        projectRepository.save(project);
        commitRepository.saveAll(projectCommits);
        mergeRequestRepository.saveAll(projectMergeRequests);

        //after all info has been collected we can now query the database to build each developers info
        setDeveloperInfo(projectId, projectSettings);
    }

    @Transactional(timeout = 1200000)
    public void setDeveloperInfo(int projectId, ProjectSettings projectSettings) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        List<Developer> projectDevs = new ArrayList<>(project.getDevelopers());

        for (Developer dev: projectDevs) {
            List<MergeRequest> devMergeRequests = mergeRequestRepository.getDevMergeRequests(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            List<MergeRequestDateScore> devMergeRequestDateScores = mergeRequestRepository.getDevsMrsScoreADay(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            List<CommitDateScore> devCommitScores = commitRepository.getDevCommitDateScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            List<CommitDateScore> devCommitScoresWithEveryDay = commitRepository.getCommitsWithEveryDateBetweenRange(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            Double devTotalCommitScore = commitRepository.userTotalCommitScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            Double devTotalMergeRequestScore = mergeRequestRepository.getUserTotalMergeRequestScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            AllScores devAllScores = new AllScores(projectSettings.getStartDate(), projectSettings.getEndDate(), devTotalCommitScore,
                    devTotalMergeRequestScore);

            //https://www.javaprogramto.com/2020/12/java-convert-localdate-to-date.html
            ZoneId systemTimeZone = ZoneId.systemDefault();
            ZonedDateTime zonedStartTime = projectSettings.getStartDate().atStartOfDay(systemTimeZone);
            ZonedDateTime zonedEndTime = projectSettings.getEndDate().atStartOfDay(systemTimeZone);
            Date startDate = Date.from(zonedStartTime.toInstant());
            Date endDate = Date.from(zonedEndTime.toInstant());

            List<Commit> devCommitsByUsername = commitRepository.findByProjectIdAndAndAuthorNameAndDateBetween(projectId,
                    dev.getUsername(), startDate, endDate);
            List<Commit> devCommitsByName = commitRepository.findByProjectIdAndAndAuthorNameAndDateBetween(projectId,
                    dev.getName(), startDate, endDate);

            List<Commit> devCommits = new ArrayList<>();
            devCommits.addAll(devCommitsByUsername);
            devCommits.addAll(devCommitsByName);

            /* Because spring generates the user object we have to make our own custom key and it cant be done in a
               constructor because of spring
             */
            dev.setDbKey(Integer.toString(projectId) +  String.valueOf(dev.getDevId()));
            dev.setProjectId(projectId);
            dev.setMergeRequestsAndCommits(devMergeRequests);
            dev.setMergeRequestDateScores(devMergeRequestDateScores);
            dev.setCommitDateScores(devCommitScores);
            dev.setCommitArray(devCommitScoresWithEveryDay);
            dev.setAllScores(devAllScores);
            dev.setCommits(devCommits);
            developerRepository.saveDev(dev);

        }
        project.setSyncInfo();
        project.setLastSyncAt();
        /* TODO I should be able to call developerRepository.saveAll(projectDevs)
            but I get an error saying that this method (.saveAll) does not exist
         */
    }

    @Transactional(timeout = 1200000)
    public void setDeveloperMrs(int projectId, ProjectSettings projectSettings) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        List<Developer> projectDevs = new ArrayList<>(project.getDevelopers());

        for (Developer dev: projectDevs) {
            List<MergeRequest> devMergeRequests = mergeRequestRepository.getDevMergeRequests(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());
            dev.setDbKey(Integer.toString(projectId) +  String.valueOf(dev.getDevId()));
            dev.setProjectId(projectId);
            dev.setMergeRequestsAndCommits(devMergeRequests);
            developerRepository.saveDev(dev);
        }
    }

    @Transactional(timeout = 1200000)
    public void setDeveloperCommits(int projectId, ProjectSettings projectSettings) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        List<Developer> projectDevs = new ArrayList<>(project.getDevelopers());

        System.out.println(projectDevs);
        for (Developer dev: projectDevs) {
            //https://www.javaprogramto.com/2020/12/java-convert-localdate-to-date.html
            ZoneId systemTimeZone = ZoneId.systemDefault();
            ZonedDateTime zonedStartTime = projectSettings.getStartDate().atStartOfDay(systemTimeZone);
            ZonedDateTime zonedEndTime = projectSettings.getEndDate().atStartOfDay(systemTimeZone);
            Date startDate = Date.from(zonedStartTime.toInstant());
            Date endDate = Date.from(zonedEndTime.toInstant());

            List<Commit> devCommitsByUsername = commitRepository.findByProjectIdAndAndAuthorNameAndDateBetween(projectId,
                    dev.getUsername(), startDate, endDate);
            List<Commit> devCommitsByName = commitRepository.findByProjectIdAndAndAuthorNameAndDateBetween(projectId,
                    dev.getName(), startDate, endDate);

            List<Commit> devCommits = new ArrayList<>();
            devCommits.addAll(devCommitsByUsername);
            devCommits.addAll(devCommitsByName);

            dev.setDbKey(Integer.toString(projectId) +  String.valueOf(dev.getDevId()));
            dev.setProjectId(projectId);
            dev.setCommits(devCommits);
            developerRepository.saveDev(dev);
        }
    }

    @Transactional(timeout = 1200000)
    public void setDevScores(int projectId, ProjectSettings projectSettings) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException(
                "Project with id " + projectId + " does not exist"));
        List<Developer> projectDevs = new ArrayList<>(project.getDevelopers());

        for (Developer dev: projectDevs) {
            List<CommitDateScore> devCommitScores = commitRepository.getDevCommitDateScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            List<MergeRequestDateScore> devMergeRequestDateScores = mergeRequestRepository.getDevsMrsScoreADay(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            Double devTotalCommitScore = commitRepository.userTotalCommitScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            Double devTotalMergeRequestScore = mergeRequestRepository.getUserTotalMergeRequestScore(projectId,
                    dev.getUsername(), projectSettings.getStartDate(), projectSettings.getEndDate());

            AllScores devAllScores = new AllScores(projectSettings.getStartDate(), projectSettings.getEndDate(), devTotalCommitScore,
                    devTotalMergeRequestScore);

            dev.setDbKey(Integer.toString(projectId) +  String.valueOf(dev.getDevId()));
            dev.setProjectId(projectId);
            dev.setMergeRequestDateScores(devMergeRequestDateScores);
            dev.setCommitDateScores(devCommitScores);
            dev.setAllScores(devAllScores);
            developerRepository.saveDev(dev);
        }
    }

    @Transactional(timeout = 1200) // 20 min
    public void saveSnapshot(Snapshot snapshot){
        snapshotRepository.save(snapshot);
    }

    public Snapshot getSnapshot(String id){
        return snapshotRepository.findById(id).orElse(null);
    }

    public List<Snapshot> getSnapshots(String username){
        return snapshotRepository.findByUsername(username);
    }

    @Transactional(timeout = 1200) // 20 min
    public void deleteSnapshot(String id){
        snapshotRepository.deleteById(id);
    }


    public List<DateScore> getDevCommitScoresPerDay(int projectId, String username, LocalDate start,
                                                    LocalDate end, UseWhichDevField devField) {
        List<Commit> allDevCommits = this.getDevCommits(projectId, username, start, end, devField);
        HashMap<String, DateScore> dateMap = new HashMap<String, DateScore>();

        for (Commit currentCommit: allDevCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        username, currentCommit.getDiffs());
                dateMap.put(commitDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(commitDate.toString());

                dateScore.addToCommitScore(currentCommit.getCommitScore());
                dateScore.incrementNumberOfCommitsBy1();
                dateScore.addCommitDiffs(currentCommit);
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
                        username, 1, mergeRequest.getDiffs());
                dateMap.put(mergedDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(mergedDate.toString());
                dateScore.addToMergeRequestScore(mergeRequest.getMrScore());
                dateScore.incrementNumMergeRequests();
                dateScore.addMergeRequestDiffs(mergeRequest);
            }
        }
        List<Commit> allDevCommits = this.getDevCommits(projectId, username, start, end, devFieldForGettingCommits);
        for (Commit currentCommit: allDevCommits) {
            LocalDate commitDate = LocalDateFunctions.convertDateToLocalDate(currentCommit.getDate());
            if(!dateMap.containsKey(commitDate.toString())) {
                DateScore dateScore = new DateScore(commitDate, currentCommit.getCommitScore(),
                        username, currentCommit.getDiffs());
                dateMap.put(commitDate.toString(), dateScore);
            } else {
                DateScore dateScore = dateMap.get(commitDate.toString());

                dateScore.addToCommitScore(currentCommit.getCommitScore());
                dateScore.incrementNumberOfCommitsBy1();
                dateScore.addCommitDiffs(currentCommit);
            }
        }
        List<DateScore> dateScores = new ArrayList<DateScore>(dateMap.values());
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

        if (developer == null || commit == null) {
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
                if (!StringFunctions.inList(commitIds, currentCommit.getCommitId())
                    && didDeveloperAuthorCommit(currentCommit, developer, devField)) {
                    devCommits.add(currentCommit);
                    commitIds.add(currentCommit.getCommitId());
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
            if (!StringFunctions.inList(commitIds, currentCommit.getCommitId())
                && currentCommit.getCommitId().equals(hash)) {
                commits.add(currentCommit);
                commitIds.add(currentCommit.getCommitId());
            }
        }
        return commits;
    }

    // TODO - For this, currently it looks like MRs that the dev only commented
    // on are returned as well. This should be fixed for calculating the score.
    public List<MergeRequest> getDevMergeRequests(int projectId, String username, LocalDate start, LocalDate end) {
        Project project = projectRepository.findProjectById(projectId);
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        List<MergeRequest> devMergeRequests = new ArrayList<>();
        Developer dev = null;
        for (Developer currentDev : getProjectDevelopers(projectId)) {
            if (currentDev.getUsername().equals(username)) {
                dev = currentDev;
                break;
            }
        }
        assert(dev != null);
        for (MergeRequest mergeRequest : mergeRequests) {
            LocalDate mergedDate = LocalDateFunctions.convertDateToLocalDate(mergeRequest.getMergedDate());
            if (mergedDate.compareTo(start) >= 0 && mergedDate.compareTo(end) <= 0
                && didDevContributeCodeToMR(mergeRequest, dev)) {
                devMergeRequests.add(mergeRequest);
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
            String whenIssueModified = issue.getModifiedAt();
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
                createdAt = LocalDate.parse(issue.getCreatedAt());
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

    public List<Note> getTopDevNotes(int projectID, String username, boolean filterByDevsCode,
                                     LocalDate start, LocalDate end, int limit, boolean applyLimit) {
        List<Note> devNotes = getDevNotes(projectID, username, filterByDevsCode, start, end);
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

    private boolean didDevContributeCodeToMR(MergeRequest mergeRequest, Developer developer) {
        if (mergeRequest == null || developer == null) {
            return false;
        }
        List<Commit> commits = mergeRequest.getCommits();
        for (Commit currentCommit: commits) {
            if (didDeveloperAuthorCommit(currentCommit, developer, UseWhichDevField.EITHER)) {
                return true;
            }
        }
        return false;
    }

    public List<Note> getDevNotes(int projectId, String username, boolean filterByDevsCode, LocalDate start, LocalDate end) {
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
        Developer developer = findDeveloperWithUsernameField(username, projectId);
        for (MergeRequest mergeRequest : mergeRequests) {
            if (filterByDevsCode && !didDevContributeCodeToMR(mergeRequest, developer)) {
                continue;
            }
            List<Note> mrNotes = mergeRequest.getAllNotes();
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
        .filter(c -> commitId.equals(c.getCommitId()))
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

    public int getTotalDevCommentWordCount(int projectId, String username, boolean filterByDevsCode,
                                            LocalDate start, LocalDate end) {
        List<Note> devNotes = this.getTopDevNotes(projectId, username, filterByDevsCode, start, end, 100000, false);
        int totalCommentWordCount = 0;
        for (Note currentNote: devNotes) {
            totalCommentWordCount += currentNote.getWordCount();
        }
        return totalCommentWordCount;
    }

    public AllScores getAllScores(int projectId, String username, boolean filterByDevsCodeForCountingComments, LocalDate startDate,
                                  LocalDate endDate, UseWhichDevField devFieldToUseForGettingCommits) {
        AllScores allScores = new AllScores(startDate, endDate, 0, 0, 0);
        double totalCommitScore = this.getTotalDevCommitScore(projectId, username, startDate,
                                                              endDate, devFieldToUseForGettingCommits);
        allScores.setTotalCommitScore(totalCommitScore);
        double totalMergeRequestScore = this.getTotalDevMRScore(projectId, username,
                                                                 startDate, endDate);
        allScores.setTotalMergeRequestScore(totalMergeRequestScore);
        int totalCommentWordCount = this.getTotalDevCommentWordCount(projectId, username,
                filterByDevsCodeForCountingComments, startDate, endDate);
        allScores.setTotalCommentWordCount(totalCommentWordCount);

        return allScores;
    }

    public List<Developer> getMembers(int ProjectId){
        Project project = projectRepository.findProjectById(ProjectId);
        return project.getDevelopers();
    }

    public List<Note> getDevNotesForChart(int projectId, String username, LocalDate start, LocalDate end)
            throws ParseException {
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
                        boolean dateAlreadyExists = false;
                        for(int i = 0; i < devNotes.size(); i++){
                            if(devNotes.get(i).getFormattedDate().equals(note.getFormattedDate())){
                                devNotes.get(i).addWordCount(note.getWordCount());
                                dateAlreadyExists = true;
                            }
                        }
                        if(!dateAlreadyExists) {
                            devNotes.add(note);
                        }
                    }
                }
            }
        }
        List<MergeRequest> mergeRequests = project.getMergedRequests();
        for (MergeRequest mergeRequest : mergeRequests) {
            List<Note> mrNotes = mergeRequest.getAllNotes();
            if (mrNotes != null) {
                for (Note note : mrNotes) {
                    LocalDate createdDate = LocalDateFunctions.convertDateToLocalDate(note.getCreatedDate());
                    if (createdDate.compareTo(start) >= 0 && createdDate.compareTo(end) <= 0
                            && didDeveloperWriteNote(note, username)) {
                        boolean dateAlreadyExists = false;
                        for(int i = 0; i < devNotes.size(); i++){
                            if(devNotes.get(i).getFormattedDate().equals(note.getFormattedDate())){
                                devNotes.get(i).addWordCount(note.getWordCount());
                                dateAlreadyExists = true;
                            }
                        }
                        if(!dateAlreadyExists) {
                            devNotes.add(note);
                        }
                    }
                }
            }
        }
        return devNotes;
    }

}
