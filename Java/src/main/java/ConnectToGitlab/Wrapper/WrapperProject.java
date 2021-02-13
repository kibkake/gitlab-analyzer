package main.java.ConnectToGitlab.Wrapper;

import com.google.gson.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * This class includes all of the important data about a repository. Merged merge
 * requests and repository issues are kept as objects in a list belonging to
 * a project object.
 */
@Document("Repository")
public class WrapperProject {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    @Id
    private int ID;
    private String projectName;
    private List<WrapperMergedMergeRequest> mergedMergeRequests;
    //private final List<WrapperCommit> ALL_COMMITS = new ArrayList<>();
    private List<WrapperIssue> issues = new ArrayList<>();
    private List<Integer> mergeRequestIds = new ArrayList<>();

    public WrapperProject() {
    }

    public WrapperProject(String token, int gitlabProjectId) throws IOException, ParseException {
        this.ID = gitlabProjectId;
        this.projectName = getProjectName(token);
        //getMergedMergeRequests(token,gitlabProjectId);
        //getAllProjectCommits(token, gitlabProjectId);
        getAllProjectIssues(token);
    }

    /**
     * Retrieves the name of a project using the project id.
     * @param token the token provided by user of the class.
     */
    private String getProjectName(String token) throws IOException {
        URL url = new URL(MAIN_URL + "/" + ID + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine) ;
        connection.disconnect();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(reply, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitiveProjectName = jsonObject.getAsJsonPrimitive("name");
        return jsonPrimitiveProjectName.getAsString();
    }

    /**
     * Retrieves all project commits, regardless of whether they were in a merge request or
     * commited directly to master.
     * @param token the token provided by user of the class.
     * @param projectId the id of the project.
     */
    private void getAllProjectCommits(String token, int projectId) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + ID + "/repository/commits" +  "?access_token=" + token);
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
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df1.parse(commitDate);
            df1.setTimeZone(TimeZone.getTimeZone("PT"));
            String date2 = df1.format(date);

            WrapperCommit commit = new WrapperCommit(token, projectId, commitId, authorName, authorEmail, title,date2, parsedCommitDate[0],
                    parsedCommitDate[1],parsedCommitDate[2]);
            //ALL_COMMITS.add(commit);
        }
    }

    /**
     * Retrieves all project merged merge request.
     * @param token the token provided by user of the class.
     * @param projectId the id of the project.
     */
    public List<WrapperMergedMergeRequest> getMergedMergeRequestsFromServer(String token, int projectId) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/merge_requests?" + "state=merged&" + "access_token=" + token);
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
        List<WrapperMergedMergeRequest> mergerRequests = new ArrayList<>();
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

            WrapperMergedMergeRequest mergeRequest = new WrapperMergedMergeRequest(token, mergeRequestId,mergeRequestIid,
                    mergeRequestProjectId, mergeRequestTitle, mergeDate[0], mergeDate[1], mergeDate[2]);
            mergeRequestIds.add(mergeRequest.getMergeRequestId());
            mergerRequests.add(mergeRequest);
        }
        return mergerRequests;
    }

    /**
     * Retrieves all project issues.
     * @param token the token provided by user of the class.
     */
    private void getAllProjectIssues(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + ID + "/issues" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);

        //System.out.println(reply);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        for(int i = 0; i< jsonArray.size(); i++) {
            JsonElement jsonElement1 = jsonArray.get(i);
            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
            JsonPrimitive jsonPrimitiveProjectId = jsonObject1.getAsJsonPrimitive("project_id");
            int projectId = jsonPrimitiveProjectId.getAsInt();
            JsonPrimitive jsonPrimitiveIssueIid = jsonObject1.getAsJsonPrimitive("iid");
            int issueIid = jsonPrimitiveIssueIid.getAsInt();
            JsonPrimitive jsonPrimitiveIssueId = jsonObject1.getAsJsonPrimitive("id");
            int issueId = jsonPrimitiveIssueId.getAsInt();
            JsonPrimitive jsonPrimitiveIssueTitle = jsonObject1.getAsJsonPrimitive("title");
            String issueTitle = jsonPrimitiveIssueTitle.getAsString();
            JsonObject jsonObjectAuthor = jsonObject1.getAsJsonObject("author");
            JsonPrimitive jsonPrimitiveAuthorName = jsonObjectAuthor.getAsJsonPrimitive("username");
            String authorName = jsonPrimitiveAuthorName.getAsString();
            JsonPrimitive jsonPrimitiveIssueDate = jsonObject1.getAsJsonPrimitive("created_at");
            String issueDate = jsonPrimitiveIssueDate.getAsString();
            int [] issueDateParsed = parsIsoDate(issueDate);


            WrapperIssue wrapperIssue = new WrapperIssue(token, projectId, issueId, issueIid, authorName, issueTitle,
                    issueDateParsed[0], issueDateParsed[1], issueDateParsed[2]);
            issues.add(wrapperIssue);
        }
    }

    /**
     * Converts an iso 8601 date into simple year, month, and day ints.
     * @param isoDate the date in iso 8601 format
     */
    private int[] parsIsoDate(String isoDate) throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date result1 = df1.parse(isoDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result1);
        int [] result = new int[3];

        result[0] = cal.get(Calendar.YEAR);
        result[1] = (cal.get(Calendar.MONTH)+1);
        result[2] = cal.get(Calendar.DATE);
        return result;
    }

    public List<String> getListOfMembers(String token) throws IOException {
        URL url = new URL(MAIN_URL + "/" + ID + "/members" +"?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        connection.disconnect();

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        List<String> userNames = new ArrayList<>();
        for(int i = 0; i< jsonArray.size(); i++) {
            JsonElement jsonElement1 = jsonArray.get(i);
            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
            JsonPrimitive jsonPrimitiveUserName = jsonObject1.getAsJsonPrimitive("username");
            String userName = jsonPrimitiveUserName.getAsString();

            userNames.add(userName);
        }
        return userNames;
    }

    /**
     * Creates a HttpURLConnection object
     * @param url the url object containing the full address of th serve
     */
    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public void addMergedMergeRequests(List<WrapperMergedMergeRequest> mergedMergeRequests){
        this.mergedMergeRequests = new ArrayList<>();
        this.mergedMergeRequests.addAll(mergedMergeRequests);
    }

    public int getID() {
        return ID;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<WrapperMergedMergeRequest> getMergedMergeRequests() {
        return mergedMergeRequests;
    }

    /*public List<WrapperCommit> getAllCommits() {
        return ALL_COMMITS;
    }*/

    public List<WrapperIssue> getIssues() {
        return issues;
    }

    public List<Integer> getMergeRequestIds() {
        return mergeRequestIds;
    }
}
