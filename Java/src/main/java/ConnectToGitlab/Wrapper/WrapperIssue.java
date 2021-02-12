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

/**
 * This class includes all of the important data about a repository issue. It contains
 * a list of notes, which represent the dicussion posts in a particular issue.
 */
public class WrapperIssue {
    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private int PROJECT_ID;
    private int ISSUE_ID;
    private int ISSUE_IID;
    private String AUTHOR_NAME;
    private String TITLE;
    private int ISSUE_YEAR;
    private int ISSUE_MONTH;
    private int ISSUE_DAY;
    private List<WrapperNote> NOTES = new ArrayList<>();

    public WrapperIssue() {
    }

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
        getIssueNotes(token);
    }

    /**
     * Retrieves a single issue's notes from the server.
     * @param token the token provided by user of the class.
     */
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
            JsonPrimitive jsonPrimitiveNoteAuthorName = jsonObjectNoteBody.getAsJsonPrimitive("username");
            String authorName = jsonPrimitiveNoteAuthorName.getAsString();
            JsonPrimitive jsonPrimitiveNoteDate = jsonObject.getAsJsonPrimitive("created_at");
            String noteDate = jsonPrimitiveNoteDate.getAsString();
            int [] noteDateParsed = parsIsoDate(noteDate);

            WrapperNote wrapperNote = new WrapperNote(noteId, noteBody, authorName, noteDateParsed[0], noteDateParsed[1],
                    noteDateParsed[2]);
            NOTES.add(wrapperNote);
        }
    }

    /**
     * Converts an iso 8601 date into simple year, month, and day ints.
     * @param isoDate the date in iso 8601 format
     */
    private static int[] parsIsoDate(String isoDate) throws ParseException {
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

    public void removeNote(int index) {
        NOTES.remove(index);
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

    public List<WrapperNote> getNotes() {
        return NOTES;
    }
}
