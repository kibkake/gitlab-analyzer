package main.java.Model;

import main.java.Model.Developer;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

@Document(value = "Note")
public class Note {

    private int id;
    private String body;
    private Developer author;
    private int score;
    private int year;
    private int month;
    private int day;
    private String username;
    private String created_at;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
        OffsetDateTime dateWithOffSet = OffsetDateTime.parse(created_at);
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
        Date result = df1.parse(created_at);

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
        System.out.println(cal.get(Calendar.YEAR) + "-" + month+ "-" + day);
        return cal.get(Calendar.YEAR) + "-" + month+ "-" + day;

    }

    public void addWordCount(int wordCount) {
        this.wordCount += wordCount;
    }
}
