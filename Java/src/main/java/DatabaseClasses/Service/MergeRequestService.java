package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Repository.MergeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;

    @Autowired
    public MergeRequestService(MergeRequestRepository mergeRequestRepository) {
        this.mergeRequestRepository = mergeRequestRepository;
    }

    @Transactional
    public void saveProjectMergeRequests(int projectId) {
        mergeRequestRepository.saveAll(MergeRequestConnection.getProjectMergeRequestsFromGitLab(projectId));
    }

    public List<CommitDateScore> getScorePerDay(int projectId, String userName) {
        return mergeRequestRepository.devsMrsADay(projectId, userName);
    }
}
