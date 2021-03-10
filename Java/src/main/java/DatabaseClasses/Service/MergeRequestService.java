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

    public MergeRequest getProjectMrByCommitHash(int projectId, String hash ) {

        String myUrl =  "http://localhost:8090/api/v2/projects/" + projectId + "/Commits/" + hash;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Commit> commitJSON = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<Commit>() {
        });

        Commit commit = Objects.requireNonNull(commitJSON.getBody());

        return mergeRequestRepository.getMrByCommitHash(projectId, hash);
        //return commit;
        //System.out.println(commit);
        //System.out.println(mergeRequestRepository.findByProjectIdAndCommitsContains(projectId,commit));
        //return mergeRequestRepository.findByProjectIdAndCommitsContains(projectId,commit);
    }

}
