package main.java.ConnectToGitlab.Wrapper;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WrapperProject {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private int gitlabProjectId;
    private String gitlabProjectName;
    private List<WrapperMergedMergeRequest> mergedMergeRequests;
    private List<WrapperCommit> allCommits;

    public WrapperProject(String token, int gitlabProjectId, String gitlabProjectName, List<WrapperMergedMergeRequest> mergedMergeRequests, List<WrapperCommit> allCommits) throws IOException {
        this.gitlabProjectId = gitlabProjectId;
        this.gitlabProjectName = gitlabProjectName;
        //this.mergedMergeRequests = mergedMergeRequests;
        getMergedMergeRequests(token, gitlabProjectId);
        this.allCommits = allCommits;
    }

    private void getMergedMergeRequests(String token, int projectId) throws IOException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/merge_requests" + "?state=merged&" + "access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        connection.disconnect();

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonPrimitive jsonPrimitiveId = jsonObject.getAsJsonPrimitive("id");
            int mergeRequestId = jsonPrimitiveId.getAsInt();
            JsonPrimitive jsonPrimitiveIid = jsonObject.getAsJsonPrimitive("iid");
            int mergeRequestIid = jsonPrimitiveIid.getAsInt();
            JsonPrimitive jsonPrimitiveProjectId = jsonObject.getAsJsonPrimitive("project_id");
            int mergeRequestProjectId = jsonPrimitiveProjectId.getAsInt();
            JsonPrimitive jsonPrimitiveMergedAt = jsonObject.getAsJsonPrimitive("merged_at");
            String mergeRequestUntilDate = jsonPrimitiveMergedAt.getAsString();

            //WrapperMergedMergeRequest mergeRequest = new WrapperMergedMergeRequest();
            //mergedMergeRequests.add(mergeRequest);

        }
    }

    public static HttpURLConnection makeConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection;
    }

    public int getGitlabProjectId() {
        return gitlabProjectId;
    }

    public String getGitlabProjectName() {
        return gitlabProjectName;
    }

    public List<WrapperMergedMergeRequest> getMergedMergeRequests() {
        return mergedMergeRequests;
    }

    public List<WrapperCommit> getAllCommits() {
        return allCommits;
    }

    public void setGitlabProjectId(int gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    public void setGitlabProjectName(String gitlabProjectName) {
        this.gitlabProjectName = gitlabProjectName;
    }

    public void setMergedMergeRequests(List<WrapperMergedMergeRequest> mergedMergeRequests) {
        this.mergedMergeRequests = mergedMergeRequests;
    }

    public void setAllCommits(List<WrapperCommit> allCommits) {
        this.allCommits = allCommits;
    }
}
