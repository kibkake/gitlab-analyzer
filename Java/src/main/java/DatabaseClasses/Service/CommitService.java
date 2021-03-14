package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.CommitConnection;
import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Repository.CommitRepository;
import main.java.Functions.LocalDateFunctions;
import main.java.Model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<CommitDateScore> getUserCommitsArray(int projectId, String authorName, LocalDate startLocalTime, LocalDate endLocalTime) {
        return commitRepository.getDevCommitArray(projectId, authorName, startLocalTime, endLocalTime);
    }

    public List<Commit> getUserCommits(int projectId, Date start, Date end, String author_name) {
        return commitRepository.findByProjectIdAndDateBetweenAndAuthorName(projectId, start, end, author_name);
    }

    public void saveProjectCommits(int projectId){
        commitRepository.saveAll(new CommitConnection().getProjectCommitsFromGitLab(projectId));
    }

    public Commit getCommit(int projectId, String commitHash) {
        return commitRepository.findByProjectIdAndId(projectId, commitHash);
    }

    public List<CommitDateScore> getScorePerDay(int projectId, String userName, LocalDate startDate, LocalDate endDate){
        return commitRepository.getDevCommitDateScore(projectId, userName, startDate, endDate);
    }

    public Object getTotalCommitScore(int projectId, String userName, LocalDate startDate, LocalDate endDate) {
        return commitRepository.userTotalCommitScore(projectId, userName, startDate, endDate);
    }

}
