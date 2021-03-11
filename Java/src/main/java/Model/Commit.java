package main.java.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.*;

/**
 *  Holds import information about commits and is used to convert JSON to an object with spring
 */

@Document(collection = "commits")
public class Commit {

    @Id //check unique per project
    private String id;
    private String title; // 1st line of commit message
    private String message; // other lines of commit message
    private String authorName;
    private String committerName;
    private String authorEmail;
    private String authorDate;
    private List<Diff> diffs;
    private Date date;
    private String sha;
    private int projectId;
    private double commitScore;



    public Commit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("committer_name")
    public String getCommitterName() {
        return committerName;
    }

    @JsonProperty("committer_name")
    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    //https://www.baeldung.com/jackson-name-of-property
    @JsonProperty("author_name")
    public String getAuthorName() {
        return authorName;
    }

    @JsonProperty("author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("author_email")
    public String getAuthorEmail() {
        return authorEmail;
    }

    @JsonProperty("author_email")
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @JsonProperty("authored_date")
    public String getAuthorDate() {
        return authorDate;
    }

    @JsonProperty("authored_date")
    public void setAuthorDate(String authorDate) {
        this.authorDate = authorDate;
        OffsetDateTime dateWithOffSet = OffsetDateTime.parse(authorDate);
        setDate(Date.from(dateWithOffSet.toInstant()));
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", author_name='" + authorName + '\'' +
                ", author_email='" + authorEmail + '\'' +
                ", authored_date=" + authorDate +
                '}';
    }

    public void setCommitScore(double commitScore) {
        this.commitScore = commitScore;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public void calculateAndSetCommitScore(){
        commitScore = 0.0;
        for (int i = 0; i < diffs.size(); i++) {
            commitScore += diffs.get(i).getDiffScore();
        }
        commitScore = Math.round(commitScore * 100.0) / 100.0;
    }
}