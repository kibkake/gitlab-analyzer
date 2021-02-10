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
@Document(collection = "Projects")
public class WrapperProject {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    @Id
    private  Integer projectId;
    private final String PROJECT_NAME;
    private final List<WrapperMergedMergeRequest> MERGED_MERGE_REQUESTS = new ArrayList<>();
    //private final List<WrapperCommit> ALL_COMMITS = new ArrayList<>();
    private final List<WrapperIssue> ALL_ISSUES = new ArrayList<>();



    public WrapperProject(String token, int gitlabProjectId) throws IOException, ParseException {
        this.projectId = gitlabProjectId;
        this.PROJECT_NAME = getProjectName(token);
        getMergedMergeRequests(token,gitlabProjectId);
        //getAllProjectCommits(token, gitlabProjectId);
        getAllProjectIssues(token);
    }

    /**
     * Retrieves the name of a project using the project id.
     * @param token the token provided by user of the class.
     */
    private String getProjectName(String token) throws IOException {
        URL url = new URL(MAIN_URL + "/" + projectId + "?access_token=" + token);
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
        URL url = new URL(MAIN_URL + "/" + projectId + "/repository/commits" +  "?access_token=" + token);
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
            //ALL_COMMITS.add(commit);
        }
    }

    /**
     * Retrieves all project merged merge request.
     * @param token the token provided by user of the class.
     * @param projectId the id of the project.
     */
    private void getMergedMergeRequests(String token, int projectId) throws IOException, ParseException {
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
            MERGED_MERGE_REQUESTS.add(mergeRequest);
        }
    }

    /**
     * Retrieves all project issues.
     * @param token the token provided by user of the class.
     */
    private void getAllProjectIssues(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/issues" + "?access_token=" + token);
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
            ALL_ISSUES.add(wrapperIssue);
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

    /**
     * Creates a HttpURLConnection object
     * @param url the url object containing the full address of th serve
     */
    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public int getGitlabProjectId() {
        return projectId;
    }

    public String getGitlabProjectName() {
        return PROJECT_NAME;
    }

    public List<WrapperMergedMergeRequest> getMergedMergeRequests() {
        return MERGED_MERGE_REQUESTS;
    }

    /*public List<WrapperCommit> getAllCommits() {
        return ALL_COMMITS;
    }*/

    public List<WrapperIssue> getAllIssues() {
        return ALL_ISSUES;
    }


}
