package main.java.ConnectToGitlab;

import main.java.ConnectToGitlab.Commit.Commit;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class NoteConnection {

    public static List<Commit> getProjectNotes(int projectId, String sinceYYYY_MM_DD, String untilYYYY_MM_DD) {
        User user = User.getInstance();

        RestTemplate restTemplate = new RestTemplate();
        String myUrl = user.getServerUrl() +"/projects/" + projectId + "/repository/commits?since=" + sinceYYYY_MM_DD + isoEnding + "&until=" + untilYYYY_MM_DD
                + isoEnding + "&with_stats=true" + "&access_token=" + user.getToken();

        // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
        ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {});
        return commitsResponse.getBody();
    }
}
