package main.java.ConnectToGitlab.Wrapper;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WrapperMergedMergeRequest {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private int mergeRequestId;
    private int mergeRequestIid;
    private int gitlabProjectId;
    private String mergeRequestTitle;
    private int mergeYear;
    private int mergeMonth;
    private int mergeDay;
    private List<WrapperCommit> mergeRequestCommits;

    public WrapperMergedMergeRequest(int mergeRequestId, int mergeRequestIid, int gitlabProjectId, String mergeRequestTitle,
                                     int mergeYear, int mergeMonth, int mergeDay){
        this.mergeRequestId = mergeRequestId;
        this.mergeRequestIid = mergeRequestIid;
        this.gitlabProjectId = gitlabProjectId;
        this.mergeRequestTitle = mergeRequestTitle;
        this.mergeYear = mergeYear;
        this.mergeMonth = mergeMonth;
        this.mergeDay = mergeDay;
        //this.mergeRequestCommits = mergeRequestCommits;
    }

    public static void getSingleMergedMergeRequestChanges(String token, int mergeIid) throws IOException {
        URL url = new URL(MAIN_URL + "/6" + "/merge_requests/" + mergeIid + "/changes" + "?access_token=" + token);
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
            JsonPrimitive jsonPrimitiveNewFileName = jsonObject1.getAsJsonPrimitive("new_path");
            singleMergedMergeDiff.add(jsonPrimitiveNewFileName.getAsString());
            //System.out.println(jsonPrimitiveNewFileName.getAsString());//file name
            JsonPrimitive jsonPrimitive = jsonObject1.getAsJsonPrimitive("diff");
            singleMergedMergeDiff.add(jsonPrimitive.getAsString());//file diff
            //System.out.println(jsonPrimitive.getAsString());
        }
    }

    public static HttpURLConnection makeConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection;
    }

    public int getMergeRequestId() {
        return mergeRequestId;
    }

    public int getMergeRequestIid() {
        return mergeRequestIid;
    }

    public int getGitlabProjectId() {
        return gitlabProjectId;
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

    public List<WrapperCommit> getMergeRequestCommits() {
        return mergeRequestCommits;
    }
}
