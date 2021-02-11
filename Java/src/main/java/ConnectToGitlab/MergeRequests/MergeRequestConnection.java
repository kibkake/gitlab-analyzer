package main.java.ConnectToGitlab.MergeRequests;//package main.java.ConnectToGitlab.MergeRequests;

import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MergeRequestConnection {

    private RestTemplate restTemplate;

    public static List<MergeRequest> getProjectMergeRequests(int projectId) {
        RestTemplate restTemplate = new RestTemplate();
        User user = User.getInstance();
        //Example: 2021-01-01T00:00:00-08:00
        //-08:00 is offset from UTC
        String isoEnding = "T00:00:00-08:00";
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/merge_requests";

        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<MergeRequest>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<MergeRequest>>() {});
        List<MergeRequest> mergeRequests = commitsResponse.getBody();
        for (MergeRequest mergeRequest : mergeRequests) {
            mergeRequest.setContributors(getMergeRequestContributors(projectId, mergeRequest.getIid()));
        }
        return mergeRequests;
    }

    public static List<Developer> getMergeRequestContributors(int projectId, int merge_request_iid) {
        User user = User.getInstance();
        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/merge_requests/" + merge_request_iid
                + "/participants";
//        ?access_token=" + user.getToken();

        ResponseEntity<List<Developer>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {});
        return commitsResponse.getBody();
    }
}
