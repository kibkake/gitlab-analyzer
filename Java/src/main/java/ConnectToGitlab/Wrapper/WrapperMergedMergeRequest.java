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

public class WrapperMergedMergeRequest {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private final int MERGE_REQUEST_ID;
    private final int MERGE_REQUEST_IID;
    private final int PROJECT_ID;
    private final String MERGE_REQUEST_TITLE;
    private final int MERGE_YEAR;
    private final int MERGE_MONTH;
    private final int MERGE_DAY;
    private double MERGE_SCORE = 0.0;
    private final List<WrapperCommit> MERGE_REQUEST_COMMITS = new ArrayList<>();
    private final List<WrapperCommitDiff> MERGE_DIFFS = new ArrayList<>();

    public WrapperMergedMergeRequest(String token, int mergeRequestId, int mergeRequestIid,
                                     int gitlabProjectId, String mergeRequestTitle, int mergeYear, int mergeMonth,
                                     int mergeDay) throws IOException, ParseException {
        this.MERGE_REQUEST_ID = mergeRequestId;
        this.MERGE_REQUEST_IID = mergeRequestIid;
        this.PROJECT_ID = gitlabProjectId;
        this.MERGE_REQUEST_TITLE = mergeRequestTitle;
        this.MERGE_YEAR = mergeYear;
        this.MERGE_MONTH = mergeMonth;
        this.MERGE_DAY = mergeDay;
        getSingleMergedMergeRequestCommits(token);
        getSingleMergedMergeRequestChanges(token);
        calculateCommitScore();
    }

    private void getSingleMergedMergeRequestCommits(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + PROJECT_ID + "/merge_requests/" + MERGE_REQUEST_IID + "/commits" + "?access_token=" + token);
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
            String commitId = jsonPrimitiveId.getAsString();
            JsonPrimitive jsonPrimitiveAuthorName = jsonObject.getAsJsonPrimitive("author_name");
            String authorName = jsonPrimitiveAuthorName.getAsString();
            JsonPrimitive jsonPrimitiveAuthorEmail = jsonObject.getAsJsonPrimitive("author_email");
            String authorEmail = jsonPrimitiveAuthorEmail.getAsString();
            JsonPrimitive jsonPrimitiveTitle = jsonObject.getAsJsonPrimitive("title");
            String title = jsonPrimitiveTitle.getAsString();
            JsonPrimitive jsonPrimitiveCommitDate = jsonObject.getAsJsonPrimitive("committed_date");
            String mergeRequestCommitDate = jsonPrimitiveCommitDate.getAsString();
            int [] mergeDate = parsIsoDate(mergeRequestCommitDate);
            WrapperCommit wrapperCommit = new WrapperCommit(token, PROJECT_ID, commitId, authorName, authorEmail, title, mergeDate[0],
                    mergeDate[1], mergeDate[2]);
            MERGE_REQUEST_COMMITS.add(wrapperCommit);
        }
    }

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

    private void getSingleMergedMergeRequestChanges(String token) throws IOException {
        URL url = new URL(MAIN_URL + "/" + PROJECT_ID + "/merge_requests/" + MERGE_REQUEST_IID + "/changes" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine) ;
        //System.out.println(reply);
        connection.disconnect();

        List<String> singleMergedMergeDiff = new ArrayList<>();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(reply, JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArrayChanges = jsonObject.getAsJsonArray("changes");
        for(int i = 0; i< jsonArrayChanges.size(); i++){
            JsonElement jsonElement1 = jsonArrayChanges.get(i);
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

            MERGE_DIFFS.add(wrapperCommitDiff);
        }
    }

    private void calculateCommitScore() {
        for(int i = 0; i < MERGE_DIFFS.size(); i++){
            MERGE_SCORE += MERGE_DIFFS.get(i).getScore();
        }
        MERGE_SCORE = Math.round(MERGE_SCORE * 100.0) / 100.0;
    }

    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public int getMergeRequestId() {
        return MERGE_REQUEST_ID;
    }

    public int getMergeRequestIid() {
        return MERGE_REQUEST_IID;
    }

    public int getProjectId() {
        return PROJECT_ID;
    }

    public String getMergeRequestTitle() {
        return MERGE_REQUEST_TITLE;
    }

    public int getMergeYear() {
        return MERGE_YEAR;
    }

    public int getMergeMonth() {
        return MERGE_MONTH;
    }

    public int getMergeDay() {
        return MERGE_DAY;
    }

    public double getMergeScore() {
        return MERGE_SCORE;
    }

    public List<WrapperCommit> getMergeRequestCommits() {
        return MERGE_REQUEST_COMMITS;
    }
}
