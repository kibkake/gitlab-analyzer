package main.java.DatabaseClasses.Service;

import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.Developer.DeveloperRepository;
import main.java.Collections.Developer;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> getProjectDevs(int projectId) {
        return developerRepository.findDevelopersByProjectId(projectId);
    }

    public List<MergeRequest> getDevMergeRequestsAndCommits(int projectId, int devId) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndDevId(projectId, devId);
        return developer.getMergeRequestsAndCommits();
    }

    public List<MergeRequestDateScore> getDevMergeRequestsScores(int projectId, int devId) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndDevId(projectId, devId);
        return developer.getMergeRequestDateScores();
    }

    public List<CommitDateScore> getDevCommitScores(int projectId, int devId) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndDevId(projectId, devId);
        return developer.getCommitDateScores();
    }
}
