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
 * This class includes all of the important data about a merged merge request, including
 * commits and notes. This class also has a list of its own merge diffs. These diffs represent
 * the changes that the merge request made to the master branch (branch it was merged into).
 */
@Document("MergedMergeRequests")
public class WrapperMergedMergeRequest {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    @Id
    private int ID;
    private int mergeRequestIid;
    private int Pid;
    private String mergeRequestTitle;
    private int mergeYear;
    private int mergeMonth;
    private int mergeDay;
    private double mergeScore = 0.0;
    private List<WrapperCommit> mergeRequestCommits = new ArrayList<>();
    private List<String> mergeRequestCommitIds = new ArrayList<>();
    private List<WrapperCommitDiff> mergeDiffs = new ArrayList<>();
    private List<WrapperNote> notes = new ArrayList<>();

    public WrapperMergedMergeRequest(String token, int mergeRequestId, int mergeRequestIid,
                                     int gitlabProjectId, String mergeRequestTitle, int mergeYear, int mergeMonth,
                                     int mergeDay) throws IOException, ParseException {
        this.ID = mergeRequestId;
        this.mergeRequestIid = mergeRequestIid;
        this.Pid = gitlabProjectId;
        this.mergeRequestTitle = mergeRequestTitle;
        this.mergeYear = mergeYear;
        this.mergeMonth = mergeMonth;
        this.mergeDay = mergeDay;
        //getSingleMergedMergeRequestCommits(token);
        getSingleMergedMergeRequestChanges(token);
        calculateCommitScore();
        getMergeNotes(token);
    }

    public WrapperMergedMergeRequest() {
    }

    public List<String> getmergeRequestCommitIds() {
        return mergeRequestCommitIds;
    }

    /**
     * Retrieves a single merged merge request from the server.
     * @param token the token provided by user of the class.
     */
    public List<WrapperCommit> getSingleMergedMergeRequestCommits(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + Pid + "/merge_requests/" + mergeRequestIid + "/commits?" +
              "per_page=100&page=1" + "&access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        int pages = Integer.parseInt(connection.getHeaderField("X-Total-Pages"));

        Gson gson = new Gson();
        List<WrapperCommit> commits = new ArrayList<>();
        BufferedReader bufferedReader;
        String reply = "";

        for (int p = 1; p <= pages; p++) {

            if(p == 1) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reply = "";
                for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);

            }else{
                url = new URL(MAIN_URL + "/" + Pid + "/merge_requests/" + mergeRequestIid + "/commits?page=1" + "&" +
                        "per_page=100&page=" + p + "&access_token=" + token);
                connection = makeConnection(url);
                connection.setRequestMethod("GET");
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reply = "";
                for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
            }

            JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
            for (int i = 0; i < jsonArray.size(); i++) {
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
                int[] mergeDate = parsIsoDate(mergeRequestCommitDate);
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = df1.parse(mergeRequestCommitDate);
                df1.setTimeZone(TimeZone.getTimeZone("PT"));
                String date2 = df1.format(date);

                WrapperCommit wrapperCommit = new WrapperCommit(token, Pid, commitId, authorName, authorEmail, title, date2,
                        mergeDate[0], mergeDate[1], mergeDate[2]);
                //MERGE_REQUEST_COMMITS.add(wrapperCommit);
                mergeRequestCommitIds.add(wrapperCommit.getId());
                commits.add(wrapperCommit);
            }
        }
        connection.disconnect();
        return commits;
    }

    /**
     * Retrieves notes belonging to a merged merge request
     * @param token the token provided by user of the class.
     */
    private void getMergeNotes(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + Pid + "/merge_requests/" + mergeRequestIid + "/notes" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        //connection.getInputStream();
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
            JsonPrimitive jsonPrimitiveNoteId = jsonObject.getAsJsonPrimitive("id");
            int noteId = jsonPrimitiveNoteId.getAsInt();
            JsonPrimitive jsonPrimitiveNoteBody = jsonObject.getAsJsonPrimitive("body");
            String noteBody = jsonPrimitiveNoteBody.getAsString();
            JsonObject jsonObjectNoteBody = jsonObject.getAsJsonObject("author");
            JsonPrimitive jsonPrimitiveNoteAuthorName = jsonObjectNoteBody.getAsJsonPrimitive("name");
            String authorName = jsonPrimitiveNoteAuthorName.getAsString();
            JsonPrimitive jsonPrimitiveNoteDate = jsonObject.getAsJsonPrimitive("created_at");
            String noteDate = jsonPrimitiveNoteDate.getAsString();
            int[] noteDateParsed = parsIsoDate(noteDate);

        WrapperNote wrapperNote = new WrapperNote(noteId, noteBody, authorName, noteDateParsed[0], noteDateParsed[1],
                noteDateParsed[2]);
        notes.add(wrapperNote);
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
     * Retrieves the changes made by a single merged merge request.
     * @param token the token provided by user of the class.
     */
    private void getSingleMergedMergeRequestChanges(String token) throws IOException {
        URL url = new URL(MAIN_URL + "/" + Pid + "/merge_requests/" + mergeRequestIid + "/changes" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        //connection.getInputStream();
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

            mergeDiffs.add(wrapperCommitDiff);
        }
    }

    /**
     * Calculates the commit score by adding up all the scores from
     * commit diffs.
     */
    private void calculateCommitScore() {
        for(int i = 0; i < mergeDiffs.size(); i++){
            mergeScore += mergeDiffs.get(i).getScore();
        }
        mergeScore = Math.round(mergeScore * 100.0) / 100.0;
    }

    public void removeCommit(int index) {
        mergeRequestCommits.remove(index);
    }

    public void removeNote(int index) {
        notes.remove(index);
    }

    /**
     * Creates a HttpURLConnection object
     * @param url the url object containing the full address of th serve
     */
    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public void addMergedMergeRequestsCommits(List<WrapperCommit> commits){
        mergeRequestCommits.addAll(commits);
    }

    public int getMergeRequestId() {
        return ID;
    }

    public int getMergeRequestIid() {
        return mergeRequestIid;
    }

    public int getPid() {
        return Pid;
    }

    public String getMergeRequestTitle() {
        return mergeRequestTitle;
    }

    public int getMergeYear() {
        return mergeYear;
    }

    public int getMergeMonth() {
        return mergeMonth;
    }

    public int getMergeDay() {
        return mergeDay;
    }

    public double getMergeScore() {
        return mergeScore;
    }

    public List<WrapperCommit> getMergeRequestCommits() {
        return mergeRequestCommits;
    }

    public List<WrapperNote> getNotes() {
        return notes;
    }

    public List<WrapperCommitDiff> getMergeDiffs() {
        return mergeDiffs;
    }
}
