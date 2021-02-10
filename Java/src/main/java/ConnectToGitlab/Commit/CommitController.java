package main.java.ConnectToGitlab.Commit;
import com.google.gson.*;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import main.java.ConnectToGitlab.Wrapper.WrapperCommit;
import main.java.ConnectToGitlab.Wrapper.WrapperCommitDiff;
import main.java.DatabaseClasses.User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Calls to GitLab Api to get Commit information
 */
@RestController
@RequestMapping(path = "api/v1/")
public class CommitController {

    /*  TODO change to autowired this is the proper way using beans, and not creating a rest template over and over
        @Autowired
        private RestTemplate restTemplate;
     */
    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }


    // Dates must be in YYYY-MM-DD
    // Dates must be between dates repo is open and must be dates that are not in the future from today
    @GetMapping("projects/{projectId}/commits/{sinceYYYY_MM_DD}/{untilYYYY_MM_DD}")
    public List<Commit> getProjectCommits(int projectId, String sinceYYYY_MM_DD, String untilYYYY_MM_DD) throws ParseException {
        if(commitService.getCommits(sinceYYYY_MM_DD, untilYYYY_MM_DD).size() == 0) {
            User user = User.getInstance();
            //Example: 2021-01-01T00:00:00-08:00
            //-08:00 is offset from UTC
            String isoEnding = "T00:00:00-08:00";
            RestTemplate restTemplate = new RestTemplate();
            String myUrl = user.getServerUrl() + "/projects/" + projectId + "/repository/commits?since=" + sinceYYYY_MM_DD + isoEnding + "&until=" + untilYYYY_MM_DD
                    + isoEnding + "&with_stats=true" + "&access_token=" + user.getToken();

            // https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
            ResponseEntity<List<Commit>> commitsResponse = restTemplate.exchange(myUrl,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Commit>>() {
                    });
            List<Commit> commits = commitsResponse.getBody();
            for (Commit singleCommit : commits) {
                singleCommit.setDiffs(WrapperCommit.getSingleCommitDiffs(projectId, mergeRequest.getIid()));
            }
        } else {
            return commitService.getCommits(sinceYYYY_MM_DD, untilYYYY_MM_DD);
        }
    }

    private void getSingleCommitDiffs(String token,  int projectId, String commitHash) throws IOException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        List<String> singleCommitDiffs = new ArrayList<>();
        for(int i = 0; i< jsonArray.size(); i++){
            JsonElement jsonElement1 = jsonArray.get(i);
            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
            JsonPrimitive jsonPrimitiveNewPath = jsonObject1.getAsJsonPrimitive("new_path");
            String newPath = jsonPrimitiveNewPath.getAsString();
            JsonPrimitive jsonPrimitiveOldPath = jsonObject1.getAsJsonPrimitive("old_path");
            String oldPath = jsonPrimitiveOldPath.getAsString();
            JsonPrimitive jsonPrimitiveNewFile = jsonObject1.getAsJsonPrimitive("new_file");
            boolean newFile = jsonPrimitiveNewFile.getAsBoolean();
            JsonPrimitive jsonPrimitiveRenamedFile = jsonObject1.getAsJsonPrimitive("renamed_file");
            boolean renamedFile = jsonPrimitiveRenamedFile.getAsBoolean();
            JsonPrimitive jsonPrimitiveDeletedFile = jsonObject1.getAsJsonPrimitive("deleted_file");
            boolean deletedFile = jsonPrimitiveDeletedFile.getAsBoolean();
            JsonPrimitive jsonPrimitiveDiff = jsonObject1.getAsJsonPrimitive("diff");
            String diff = jsonPrimitiveDiff.getAsString();
            WrapperCommitDiff wrapperCommitDiff = new WrapperCommitDiff(newPath, oldPath, newFile, renamedFile,
                    deletedFile, diff);
            COMMIT_DIFFS.add(wrapperCommitDiff);
        }
    }

}
