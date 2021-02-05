package main.java.ConnectToGitlab.Wrapper;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WrapperCommit {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private String id;
    private String authorName;
    private String authorEmail;
    private String title;
    private int commitYear;
    private int commitMonth;
    private int commitDay;
    private double commitScore = 0.0;
    private List<WrapperCommitDiff> wrapperCommitDiffs = new ArrayList<>();


    public WrapperCommit(String token, int projectId, String id, String authorName, String authorEmail, String title,
                         int commitYear, int commitMonth, int commitDay) throws IOException {
        this.id = id;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.title = title;
        this.commitYear = commitYear;
        this.commitMonth = commitMonth;
        this.commitDay = commitDay;
        getSingleCommitDiffs(token, projectId, id);
        calculateCommitScore();
    }

    public void getSingleCommitDiffs(String token,  int projectId, String commitHash) throws IOException {
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
            wrapperCommitDiffs.add(wrapperCommitDiff);
        }
    }

    public void calculateCommitScore(){
        for(int i = 0; i < wrapperCommitDiffs.size(); i++){
            commitScore += wrapperCommitDiffs.get(i).getScore();
        }
    }

    public static HttpURLConnection makeConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection;
    }

    public String getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getTitle() {
        return title;
    }

    public int getCommitYear() {
        return commitYear;
    }

    public int getCommitMonth() {
        return commitMonth;
    }

    public int getCommitDay() {
        return commitDay;
    }

    public List<WrapperCommitDiff> getWrapperCommitDiffs() {
        return wrapperCommitDiffs;
    }
}
