package main.java.Collections;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 *  Holds import information about commits and is used to convert JSON to an object with spring
 */

@Document(collection = "commits")
public class Commit {

    @Id //check unique per project
    private String commitId;
    private String title; // 1st line of commit message
    private String message; // other lines of commit message
    private String authorName;
    private String committerName;
    private List<Diff> diffs;
    private Date date;
    private String sha;
    private int projectId;
    private double commitScore;

    public Commit() {
    }
    @JsonProperty("committed_date")
    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
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
    @JsonProperty("committed_date")
    public Date getDate() {
        return date;
    }

    @JsonProperty("committed_date")
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
                "id='" + commitId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", author_name='" + authorName + '\'' +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commit commit = (Commit) o;
        return (projectId == commit.projectId && Double.compare(commit.commitScore, commitScore) == 0
                && Objects.equals(commitId, commit.commitId) && Objects.equals(title, commit.title)
                && Objects.equals(message, commit.message)
                && Objects.equals(authorName, commit.authorName)
                && Objects.equals(committerName, commit.committerName)
                && Objects.equals(diffs, commit.diffs) && Objects.equals(date, commit.date)
                && Objects.equals(sha, commit.sha));
    }
}