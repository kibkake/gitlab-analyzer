package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.MergeRequestRepository;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
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

    public List<MergeRequestDateScore> getMrScorePerDay(int projectId, String userName, LocalDate startDate, LocalDate endDate) {
        return mergeRequestRepository.devsMrsScoreADay(projectId, userName, startDate, endDate);
    }

    public MergeRequest getMergeRequest(int projectId, int mrId) {
        return mergeRequestRepository.findByProjectIdAndId(projectId, mrId);
    }

    public List<MergeRequest> getProjectMRs(int projectId) {
        return mergeRequestRepository.findByProjectId(projectId);
    }

    public List<MergeRequest> getUserMergeRequests(int projectId, String authorName, Date startLocalTime,
                                                      Date endLocalTime) {
        return mergeRequestRepository.findByProjectIdAndAuthorUsernameAndMergedDateBetween(projectId, authorName,
                startLocalTime, endLocalTime);
    }

    public Object getTotalMergeRequestScore(int projectId, String authorName, LocalDate startLocalTime,
                                                  LocalDate endLocalTime) {
        return mergeRequestRepository.userTotalMergeRequestScore(projectId, authorName,
                startLocalTime, endLocalTime);
    }
}
