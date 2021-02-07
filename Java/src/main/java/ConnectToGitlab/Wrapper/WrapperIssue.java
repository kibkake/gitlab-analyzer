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

public class WrapperIssue {
    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private final int PROJECT_ID;
    private final int ISSUE_ID;
    private final int ISSUE_IID;
    private final String AUTHOR_NAME;
    private final String TITLE;
    private final int ISSUE_YEAR;
    private final int ISSUE_MONTH;
    private final int ISSUE_DAY;

    public WrapperIssue(String token, int projectId, int issueId, int issueIid, String authorName, String title,
                        int year, int month, int day) throws IOException, ParseException {
        this.PROJECT_ID = projectId;
        this.ISSUE_ID = issueId;
        this.ISSUE_IID = issueIid;
        this.AUTHOR_NAME = authorName;
        this.TITLE = title;
        this.ISSUE_YEAR = year;
        this.ISSUE_MONTH = month;
        this.ISSUE_DAY = day;
        //getIssueNotes(token);
    }

    private void getIssueNotes(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + PROJECT_ID + "/issues/" + ISSUE_IID + "/notes" + "?access_token=" + token);
        HttpURLConnection connection = makeConnection(url);
        connection.setRequestMethod("GET");
        connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String reply = "";
        for (String oneLine; (oneLine = bufferedReader.readLine()) != null; reply += oneLine);
        //System.out.println(reply);
        connection.disconnect();

    }

    private static HttpURLConnection makeConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public int getProjectId() {
        return PROJECT_ID;
    }

    public int getIssueId() {
        return ISSUE_ID;
    }

    public int getIssueIid() {
        return ISSUE_IID;
    }

    public String getAuthorName() {
        return AUTHOR_NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public int getIssueYear() {
        return ISSUE_YEAR;
    }

    public int getIssueMonth() {
        return ISSUE_MONTH;
    }

    public int getIssueDay() {
        return ISSUE_DAY;
    }
}
