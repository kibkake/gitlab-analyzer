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

    private static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private final String ID;
    private final String AUTHOR_NAME;
    private final String AUTHOR_EMAIL;
    private final String TITLE;
    private final int COMMIT_YEAR;
    private final int COMMIT_MONTH;
    private final int COMMIT_DAY;
    private double commitScore = 0.0;
    private final List<WrapperCommitDiff> COMMIT_DIFFS = new ArrayList<>();


    public WrapperCommit(String token, int projectId, String id, String authorName, String authorEmail, String title,
                         int commitYear, int commitMonth, int commitDay) throws IOException {
        this.ID = id;
        this.AUTHOR_NAME = authorName;
        this.AUTHOR_EMAIL = authorEmail;
        this.TITLE = title;
        this.COMMIT_YEAR = commitYear;
        this.COMMIT_MONTH = commitMonth;
        this.COMMIT_DAY = commitDay;
        getSingleCommitDiffs(token, projectId, id);
        calculateCommitScore();
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

    private void calculateCommitScore(){
        for(int i = 0; i < COMMIT_DIFFS.size(); i++){
            commitScore += COMMIT_DIFFS.get(i).getScore();
        }
        commitScore = Math.round(commitScore * 100.0) / 100.0;

    }

    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public String getId() {
        return ID;
    }

    public String getAuthorName() {
        return AUTHOR_NAME;
    }

    public String getAuthorEmail() {
        return AUTHOR_EMAIL;
    }

    public String getTitle() {
        return TITLE;
    }

    public int getCommitYear() {
        return COMMIT_YEAR;
    }

    public int getCommitMonth() {
        return COMMIT_MONTH;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public int getCommitDay() {
        return COMMIT_DAY;
    }

    public List<WrapperCommitDiff> getWrapperCommitDiffs() {
        return COMMIT_DIFFS;
    }
}
