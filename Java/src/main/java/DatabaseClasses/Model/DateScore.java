package main.java.DatabaseClasses.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class DateScore {
    private LocalDate date;
    private double commitScore;
    private double mergeRequestScore;
    private String authorName;
    private int numCommits;
    private int numMergeRequests;
    private List<Integer> mergeRequestId;
    private List<String> commitIds;
    private List<Integer> mergeRequestIds;

    public DateScore() {
    }

    public DateScore(LocalDate date, double commitScore, String authorName, String id) {
        this.date = date;
        this.commitScore = commitScore;
        this.authorName = authorName;
        this.numCommits = 1;
        this.commitIds = new ArrayList<>();
        this.mergeRequestIds = new ArrayList<Integer>();
        commitIds.add(id);
    }

    public DateScore(LocalDate date, double score, String authorName, Integer numMergeRequests, Integer mergeRequestId) {
        this.date = date;
        this.mergeRequestScore = score;
        this.authorName = authorName;
        this.numMergeRequests = numMergeRequests;
        this.mergeRequestIds = new ArrayList<Integer>();
        this.commitIds = new ArrayList<>();
        this.mergeRequestIds.add(mergeRequestId);
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

    public double getMergeRequestScore() {
        return mergeRequestScore;
    }

    public void setMergeRequestScore(double mergeRequestScore) {
        this.mergeRequestScore = mergeRequestScore;
    }

    public int getNumMergeRequests() {
        return numMergeRequests;
    }

    public void setNumMergeRequests(int numMergeRequests) {
        this.numMergeRequests = numMergeRequests;
    }

    public List<Integer> getMergeRequestId() {
        return mergeRequestId;
    }

    public void setMergeRequestId(List<Integer> mergeRequestId) {
        this.mergeRequestId = mergeRequestId;
    }

    public List<Integer> getMergeRequestIds() {
        return mergeRequestIds;
    }

    public void setMergeRequestIds(List<Integer> mergeRequestIds) {
        this.mergeRequestIds = mergeRequestIds;
    }

    public void addToCommitScore(Double score) {
        this.commitScore = this.commitScore + score;
    }


    public void addToMergeRequestScore(Double score) {
        this.mergeRequestScore = this.mergeRequestScore + score;
    }

    public void incrementNumMergeRequests() {
        this.numMergeRequests = this.numMergeRequests + 1;
    }

    public void addMergeRequestIds(int id) {
        this.mergeRequestIds.add(id);
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
}
