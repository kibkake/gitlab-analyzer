package main.java.DatabaseClasses.Service;

import main.java.ConnectToGitlab.MergeRequestConnection;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import main.java.DatabaseClasses.Repository.MergeRequestRepository;
import main.java.Model.Commit;
import main.java.Model.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;

    @Autowired
    public MergeRequestService(MergeRequestRepository mergeRequestRepository) {
        this.mergeRequestRepository = mergeRequestRepository;
    }

    @Transactional
    public void saveProjectMergeRequests(int projectId) {
        mergeRequestRepository.saveAll(new MergeRequestConnection().getProjectMergeRequestsFromGitLab(projectId));
    }

    public List<MergeRequestDateScore> getMrScorePerDay(int projectId, String userName, LocalDate startDate, LocalDate endDate) {
        return mergeRequestRepository.getDevsMrsScoreADay(projectId, userName, startDate, endDate);
    }

    public MergeRequest getMergeRequest(int projectId, int mrId) {
        return mergeRequestRepository.findByProjectIdAndId(projectId, mrId);
    }

    public List<MergeRequest> getProjectMRs(int projectId) {
        return mergeRequestRepository.findByProjectId(projectId);
    }

    public List<MergeRequest> getUserMergeRequests(int projectId, String authorName, LocalDate startLocalTime,
                                                   LocalDate endLocalTime) {
        return mergeRequestRepository.getDevMergeRequests(projectId, authorName,
                startLocalTime, endLocalTime);
    }

    public Object getTotalMergeRequestScore(int projectId, String authorName, LocalDate startLocalTime,
                                                  LocalDate endLocalTime) {
        return mergeRequestRepository.getUserTotalMergeRequestScore(projectId, authorName,
                startLocalTime, endLocalTime);
    }

    public MergeRequest getMrByCommitHash(int projectId, String hash) {

        return mergeRequestRepository.getMrByCommitHash(projectId, hash);

    }

}
