package main.java.DatabaseClasses.Scores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class CommitDateScore implements Comparable<CommitDateScore>{
    private LocalDate date;
    private double commitScore;
    private String authorName;
    private int numCommits;
    private List<String> commitIds;

    public CommitDateScore() {
    }

    public CommitDateScore(LocalDate date, double commitScore, String authorName, String id) {
        this.date = date;
        this.commitScore = commitScore;
        this.authorName = authorName;
        this.numCommits = 0;
        this.commitIds = new ArrayList<>();
        commitIds.add(id);
    }

    public CommitDateScore(LocalDate date, double score, int numCommits, String authorName) {
        this.date = date;
        this.commitScore = score;
        this.numCommits = numCommits;
        this.authorName = authorName;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCommitScore(double commitScore) {
        this.commitScore = commitScore;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void addToCommitScore(double score) {
        this.commitScore += score;
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


    public void addToCommitScore(Double score) {
        this.commitScore = this.commitScore + score;
    }


    @Override
    public String toString() {
        return "DateScore{" +
                "date=" + date +
                ", commitScore=" + commitScore +
                ", userName='" + authorName + '\'' +
                ", numCommits=" + numCommits +
                '}';
    }

    //https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
    @Override
    public int compareTo(CommitDateScore o) {
        if (getDate() == null || o.getDate() == null) {
            return 0;
        } else {
            return getDate().compareTo(o.getDate());
        }
    }
}
