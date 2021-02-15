package main.java.DatabaseClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class CommitDateScore {
    private LocalDate date;
    private double score;
    private String userName;
    private int numCommits;
    private List<String> commitIds;

    public CommitDateScore(LocalDate date, double score, String userName, String id) {
        this.date = date;
        this.score = score;
        this.userName = userName;
        this.numCommits = 1;
        this.commitIds = new ArrayList<>();
        commitIds.add(id);
    }

    public LocalDate getDate() {
        return date;
    }

    public double getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addToScore(double score) {
        this.score += score;
    }

    public int getNumCommits() {
        return numCommits;
    }

    public void setNumCommits(int numCommits) {
        this.numCommits = numCommits;
    }

    public void incrementNumberOfCommitsBy1() {
        this.numCommits = this.numCommits + 1;
    }

    public List<String> getCommitIds() {
        return commitIds;
    }

    public void setCommitIds(List<String> commitIds) {
        this.commitIds = commitIds;
    }

    public void addCommitIds(String id) {
        this.commitIds.add(id);
    }

    @Override
    public String toString() {
        return "DateScore{" +
                "date=" + date +
                ", commitScore=" + score +
                ", userName='" + userName + '\'' +
                ", numCommits=" + numCommits +
                '}';
    }
}
