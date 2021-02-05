package main.java.ConnectToGitlab.Wrapper;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WrapperProject {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private int gitlabProjectId;
    private String gitlabProjectName;
    private List<WrapperMergedMergeRequest> mergedMergeRequests = new ArrayList<>();
    private List<WrapperCommit> allCommits = new ArrayList<>();

    public WrapperProject(String token, int gitlabProjectId, String gitlabProjectName) throws IOException, ParseException {
        this.gitlabProjectId = gitlabProjectId;
        this.gitlabProjectName = gitlabProjectName;
        getMergedMergeRequests(token,gitlabProjectId);
        getAllProjectCommits(token, gitlabProjectId);
    }

    private void getAllProjectCommits(String token, int projectId) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + gitlabProjectId + "/repository/commits" +  "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine) ;
        //System.out.println(reply);
        connection.disconnect();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonPrimitive jsonPrimitiveId = jsonObject.getAsJsonPrimitive("id");
            String commitId = jsonPrimitiveId.getAsString();
            JsonPrimitive jsonPrimitiveAuthorName = jsonObject.getAsJsonPrimitive("author_name");
            String authorName = jsonPrimitiveAuthorName.getAsString();
            JsonPrimitive jsonPrimitiveAuthorEmail = jsonObject.getAsJsonPrimitive("author_email");
            String authorEmail = jsonPrimitiveAuthorEmail.getAsString();
            JsonPrimitive jsonPrimitiveTitle = jsonObject.getAsJsonPrimitive("title");
            String title = jsonPrimitiveTitle.getAsString();
            JsonPrimitive jsonPrimitiveCommitDate = jsonObject.getAsJsonPrimitive("committed_date");
            String commitDate = jsonPrimitiveCommitDate.getAsString();
            int [] parsedCommitDate = parsIsoDate(commitDate);


            WrapperCommit commit = new WrapperCommit(token, projectId, commitId, authorName, authorEmail, title, parsedCommitDate[0],
                    parsedCommitDate[1],parsedCommitDate[2]);
            allCommits.add(commit);
        }
    }

    private void getMergedMergeRequests(String token, int projectId) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + gitlabProjectId + "/merge_requests?" + "state=merged&" + "access_token=" + token);
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
            JsonPrimitive jsonPrimitiveTitle = jsonObject.getAsJsonPrimitive("title");
            String mergeRequestTitle = jsonPrimitiveTitle.getAsString();
            int [] mergeDate = parsIsoDate(mergeRequestUntilDate);

            WrapperMergedMergeRequest mergeRequest = new WrapperMergedMergeRequest(token, projectId, mergeRequestId,mergeRequestIid,
                    mergeRequestProjectId, mergeRequestTitle, mergeDate[0], mergeDate[1], mergeDate[2]);
            mergedMergeRequests.add(mergeRequest);
        }
    }

    public static int[] parsIsoDate(String isoDate) throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        //Date result1 = df1.parse("2024-01-24T23:55:59.000+00:00");
        Date result1 = df1.parse(isoDate);
        //System.out.println(result1);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result1);
        int [] result = new int[3];

        result[0] = cal.get(Calendar.YEAR);
        result[1] = (cal.get(Calendar.MONTH)+1);
        result[2] = cal.get(Calendar.DATE);
        return result;
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
