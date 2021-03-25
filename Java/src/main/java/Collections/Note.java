package main.java.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

@Document(value = "Note")
public class Note {

    private int id;
    private String body;
    private Developer author;
    private int score;
    private String username;
    private String createdAt;
    private Date createdDate;
    private Boolean isIssueNote;
    private int wordCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Developer getAuthor() {
        return author;
    }

    public void setAuthor(Developer author) {
        this.author = author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        OffsetDateTime dateWithOffSet = OffsetDateTime.parse(createdAt);
        setCreatedDate(Date.from(dateWithOffSet.toInstant()));
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIssueNote() {
        return isIssueNote;
    }

    public void setIssueNote(Boolean issueNote) {
        isIssueNote = issueNote;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int countWords(String noteBody) {
        int numWords = new StringTokenizer(noteBody).countTokens();
        return numWords;
    }

    public String getFormattedDate() throws ParseException{

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df1.setTimeZone(TimeZone.getTimeZone("PT"));
        Date result = createdDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(result);

        String month = Integer.toString((cal.get(Calendar.MONTH)+1));
        String day = Integer.toString(cal.get(Calendar.DATE));

        if(month.length() < 2){
            month = "0" + Integer.toString((cal.get(Calendar.MONTH)+1));
        }
        if(day.length() < 2){
            day = "0" + Integer.toString(cal.get(Calendar.DATE));
        }
        return cal.get(Calendar.YEAR) + "-" + month+ "-" + day;

    }

    public void addWordCount(int wordCount) {
        this.wordCount += wordCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Note note = (Note) o;
        return (id == note.id && score == note.score && wordCount == note.wordCount
                && Objects.equals(body, note.body) && Objects.equals(author, note.author)
                && Objects.equals(username, note.username)
                && Objects.equals(createdAt, note.createdAt)
                && Objects.equals(createdDate, note.createdDate)
                && Objects.equals(isIssueNote, note.isIssueNote));
    }
}
