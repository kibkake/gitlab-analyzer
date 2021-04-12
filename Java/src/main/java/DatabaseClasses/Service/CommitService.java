package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.CommitConnection;
import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Repository.Commit.CommitRepository;
import main.java.Collections.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<CommitDateScore> getUserCommitsArray(int projectId, String authorName, LocalDateTime startLocalTime, LocalDateTime endLocalTime) {
        return commitRepository.getCommitsWithEveryDateBetweenRange(projectId, authorName, startLocalTime, endLocalTime);
    }

    public List<Commit> getUserCommits(int projectId, String authorName, LocalDateTime start, LocalDateTime end) {
        return commitRepository.findByProjectIdAndAndAuthorNameAndDateBetween(projectId, authorName, start, end);
    }

    public void saveProjectCommits(int projectId){
        commitRepository.saveAll(new CommitConnection().getProjectCommitsFromGitLab(projectId));
    }

    public Commit getCommit(int projectId, String commitHash) {
        return commitRepository.findByProjectIdAndCommitId(projectId, commitHash);
    }

    public List<CommitDateScore> getScorePerDay(int projectId, String userName, LocalDateTime startDate, LocalDateTime endDate){
        return commitRepository.getDevCommitDateScore(projectId, userName, startDate, endDate);
    }

    public Object getTotalCommitScore(int projectId, String userName, LocalDateTime startDate, LocalDateTime endDate) {
        return commitRepository.userTotalCommitScore(projectId, userName, startDate, endDate);
    }

}
