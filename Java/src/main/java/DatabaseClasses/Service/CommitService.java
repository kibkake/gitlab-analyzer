package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.CommitConnection;
import main.java.DatabaseClasses.Model.DateScore;
import main.java.DatabaseClasses.Repository.CommitRepository;
import main.java.DatabaseClasses.Repository.ProjectRepository;
import main.java.Model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<Commit> getUserCommits(int projectId, Date start, Date end, String author_name) {
        return commitRepository.findByProjectIdAndDateBetweenAndAuthorName(projectId, start, end, author_name);
    }

    public void saveProjectCommits(int projectId){
        commitRepository.saveAll(CommitConnection.getProjectCommitsFromGitLab(projectId));
    }

    public Commit getCommit(int projectId, String commitHash) {
        return commitRepository.findByProjectIdAndId(projectId, commitHash);
    }

    public List<DateScore> getScorePerDay(int projectId, String userName){
        return commitRepository.getDevDateScore(projectId, userName);
    }
}
