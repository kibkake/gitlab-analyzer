package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.CommitConnection;
import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Repository.Commit.CommitRepository;
import main.java.Collections.Commit;
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
        return commitRepository.getCommitsWithEveryDateBetweenRange(projectId, authorName, startLocalTime, endLocalTime);
    }


    public void saveProjectCommits(int projectId){
        commitRepository.saveAll(new CommitConnection().getProjectCommitsFromGitLab(projectId));
    }

    public Commit getCommit(int projectId, String commitHash) {
        return commitRepository.findByProjectIdAndCommitId(projectId, commitHash);
    }


}
