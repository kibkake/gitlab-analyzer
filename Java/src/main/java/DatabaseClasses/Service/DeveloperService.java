package main.java.DatabaseClasses.Service;

import main.java.Collections.Commit;
import main.java.DatabaseClasses.Scores.CommitDateScore;
import main.java.DatabaseClasses.Scores.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.Developer.DeveloperRepository;
import main.java.Collections.Developer;
import main.java.Collections.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<String> getProjectDevUsernames(int projectId) {
        List<Developer> members = developerRepository.findDevelopersByProjectId(projectId);
        List<String> memberUsernames = new ArrayList<>();

        for (int i = 0; i < members.size(); i++){
            memberUsernames.add(members.get(i).getUsername());
        }
        java.util.Collections.sort(memberUsernames);
        return memberUsernames;
    }

    public List<MergeRequest> getDevMergeRequestsAndCommits(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getMergeRequestsAndCommits();
    }

    public List<MergeRequestDateScore> getDevMergeRequestsScores(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getMergeRequestDateScores();
    }

    public List<CommitDateScore> getDevCommitScores(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getCommitDateScores();
    }

    public List<Commit> getDevCommits(int projectId, String username) {
        Developer developer = developerRepository.findDeveloperByProjectIdAndUsername(projectId, username);
        return developer.getCommits();
    }
}
