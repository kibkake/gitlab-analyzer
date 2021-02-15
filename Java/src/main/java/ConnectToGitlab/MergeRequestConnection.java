package main.java.ConnectToGitlab;//package main.java.ConnectToGitlab.MergeRequests;

import main.java.Model.Commit;
import main.java.Model.Developer;
import main.java.Model.MergeRequest;
import main.java.Model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MergeRequestConnection {

    public static List<MergeRequest> getProjectMergeRequests(int projectId) {
        RestTemplate restTemplate = new RestTemplate();
        User user = User.getInstance();
        //Example: 2021-01-01T00:00:00-08:00
        //-08:00 is offset from UTC
        String isoEnding = "T00:00:00-08:00";
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/merge_requests?access_token=" + user.getToken();

        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<MergeRequest>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<MergeRequest>>() {});
        List<MergeRequest> mergeRequests = commitsResponse.getBody();
        for (MergeRequest mergeRequest : mergeRequests) {
            mergeRequest.setContributors(getMergeRequestContributors(projectId, mergeRequest.getIid()));
            mergeRequest.setCommits(getMergeRequestCommits(projectId, mergeRequest.getIid()));
            mergeRequest.setScore(calcMergeRequestScore(mergeRequest.getCommits())); // done after commit list gnerated
        }
        return mergeRequests;
    }

    public static List<Developer> getMergeRequestContributors(int projectId, int merge_request_iid) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String url = user.getServerUrl() +"/projects/" + projectId + "/merge_requests/" + merge_request_iid
                + "/participants?access_token=" + user.getToken();
//        ?access_token=" + user.getToken();

        ResponseEntity<List<Developer>> commitsResponse = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        return commitsResponse.getBody();
    }

    public static List<Commit> getMergeRequestCommits(int projectId, int mergeRequestIid) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String url = (user.getServerUrl() +"projects/"  + projectId  + "/merge_requests/" + mergeRequestIid + "/commits"
                + "?access_token=" + user.getToken());

        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(url,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        List<Commit> mergeCommits = commitsResponse.getBody();

        for (Commit singleCommit : mergeCommits) {
            singleCommit.setProjectId(projectId); // sets projectId if removing set project id a different way
            singleCommit.setDiffs(CommitConnection.getSingleCommitDiffs(projectId, singleCommit.getId()));
            singleCommit.calculateAndSetCommitScore(); // done after getting commits
        }
        return mergeCommits;
    }

    public static double calcMergeRequestScore(List<Commit> commits) {
        double score = 0;
        for (Commit commit: commits) {
            score += commit.getCommitScore();
        }
        return score;
    }



}
