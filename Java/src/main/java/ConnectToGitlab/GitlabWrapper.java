package main.java.ConnectToGitlab;

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

public class GitlabWrapper {

    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    public static int sinceYear = 2000;
    public static int sinceMonth = 1;
    public static int sinceDay = 1;
    public static int untilYear = 2021;
    public static int untilMonth = 2;
    public static int untilDay = 24;

    public static void setSinceDates(int sYear, int sMonth, int sDay){
        sinceYear = sYear;
        sinceMonth = sMonth;
        sinceDay = sDay;
    }

    public static void setUntilDates(int uYear, int uMonth, int uDay){
        untilYear = uYear;
        untilMonth = uMonth;
        untilDay = uDay;
    }

    public static HttpURLConnection makeConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection;
    }


    public static void getListOfMembershipProjects(String token) throws IOException {
        URL url = new URL(MAIN_URL + "?membership&" + "order_by=name&" + "simple=true" +"&access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        connection.disconnect();

    }

    public static void getMergedMergeRequests(String token, int projectId) throws IOException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/merge_requests" + "?state=merged&" + "access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        connection.disconnect();

    }

    public static void getSingleMergedMergeRequestCommits(String token, int mergeIid) throws IOException {
        URL url = new URL(MAIN_URL + "/6" + "/merge_requests/" + mergeIid + "/commits?" + "access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        getUserCommits("arahilin", reply);
        connection.disconnect();
    }

    public static void getUserCommits(String username, String jsonString) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
        List<String> userCommitHashes = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonPrimitive jsonPrimitiveName = jsonObject.getAsJsonPrimitive("committer_name");
            if (jsonPrimitiveName.getAsString().equals(username)) {
                JsonPrimitive jsonPrimitiveId = jsonObject.getAsJsonPrimitive("id");
                userCommitHashes.add(jsonPrimitiveId.getAsString());
            }
        }
        //System.out.println(userCommitHashes);
    }

    public static void getSingleCommitDiffs(String token,  int projectId, String commitHash) throws IOException {
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
            JsonPrimitive jsonPrimitiveNewFileName = jsonObject1.getAsJsonPrimitive("new_path");
            singleCommitDiffs.add(jsonPrimitiveNewFileName.getAsString());
            //System.out.println(jsonPrimitiveNewFileName.getAsString());//file name
            JsonPrimitive jsonPrimitive = jsonObject1.getAsJsonPrimitive("diff");
            singleCommitDiffs.add(jsonPrimitive.getAsString());//file diff
            //System.out.println(jsonPrimitive.getAsString());
        }
    }

    public static void getSingleMergedMergeRequestChanges(String token, int mergeIid) throws IOException {
        URL url = new URL(MAIN_URL + "/6" + "/merge_requests/" + mergeIid + "/changes?" + "access_token=" + token);
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

    public static void getAllProjectIssues(String token, int mergeIid) throws IOException {
        URL url = new URL(MAIN_URL + "/" + mergeIid + "/issues" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);

        //System.out.println(reply);
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(reply, JsonArray.class);
        List<Integer> issueIids = new ArrayList<>();
        for(int i = 0; i< jsonArray.size(); i++) {
            JsonElement jsonElement1 = jsonArray.get(i);
            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
            JsonPrimitive jsonPrimitiveIssueIid = jsonObject1.getAsJsonPrimitive("iid");
            //System.out.println(jsonPrimitiveIssueIid.getAsInt());
            issueIids.add(jsonPrimitiveIssueIid.getAsInt());
            JsonPrimitive jsonPrimitiveIssueTitle = jsonObject1.getAsJsonPrimitive("title");
            //System.out.println(jsonPrimitiveIssueTitle.getAsString());

        }
    }

    public static void getAllCommitBetweenDates(String token, int projectId) throws IOException {
        URL url = new URL(MAIN_URL + "/" + projectId + "/repository/commits" + "?since=" + sinceYear + "-" +
                sinceMonth + "-" + sinceDay + "T00:00:00.000-08:00&" + "until=" + untilYear + "-" + untilMonth + "-" +
                untilDay + "T23:59:59.000-08:00&" + "access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine) ;
        //System.out.println(reply);
        connection.disconnect();

    }

    public static void parsIsoDate(String isoDate) throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        //Date result1 = df1.parse("2024-01-24T23:55:59.000+00:00");
        Date result1 = df1.parse(isoDate);

        //System.out.println(result1);

        Calendar cal = Calendar.getInstance();
        cal.setTime(result1);

        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH)+1);
        System.out.println(cal.get(Calendar.DATE));
    }

    public static void getMergedMergeRequestsBetweenDates(String token, int projectId) throws IOException, ParseException {
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
        for(int i = 0; i< jsonArray.size(); i++) {
            JsonElement jsonElement1 = jsonArray.get(i);
            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
            JsonPrimitive jsonPrimitiveDate = jsonObject1.getAsJsonPrimitive("merged_at");
            System.out.println(jsonPrimitiveDate.getAsString());
            parsIsoDate(jsonPrimitiveDate.getAsString());

        }

    }




}


