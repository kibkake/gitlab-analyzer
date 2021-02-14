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
    private int pId;
    private int issueId;
    private int issueIid;
    private String authorName;
    private String title;
    private int issueYear;
    private int issueMonth;
    private int issueDay;
    private List<WrapperNote> notes = new ArrayList<>();

    public WrapperIssue() {
    }

    public WrapperIssue(String token, int projectId, int issueId, int issueIid, String authorName, String title,
                        int year, int month, int day) throws IOException, ParseException {
        this.pId = projectId;
        this.issueId = issueId;
        this.issueIid = issueIid;
        this.authorName = authorName;
        this.title = title;
        this.issueYear = year;
        this.issueMonth = month;
        this.issueDay = day;
        getIssueNotes(token);
    }

    /**
     * Retrieves a single issue's notes from the server.
     * @param token the token provided by user of the class.
     */
    private void getIssueNotes(String token) throws IOException, ParseException {
        URL url = new URL(MAIN_URL + "/" + pId + "/issues/" + issueIid + "/notes" + "?access_token=" + token);
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
            notes.add(wrapperNote);
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
        notes.remove(index);
    }

    public int getProjectId() {
        return pId;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getIssueIid() {
        return issueIid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public int getIssueYear() {
        return issueYear;
    }

    public int getIssueMonth() {
        return issueMonth;
    }

    public int getIssueDay() {
        return issueDay;
    }

    public List<WrapperNote> getNotes() {
        return notes;
    }
}
