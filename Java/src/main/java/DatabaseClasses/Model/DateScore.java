package main.java.DatabaseClasses.Model;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import main.java.Model.*;
/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class DateScore {
    private LocalDate date;
    private double commitScore;
    private double mergeRequestScore;
    private String userName;
    private int numCommits;
    private int numMergeRequests;
    private List<MergeRequest> mergeRequestId;
    private List<Commit> commitIds;
    private ArrayList<MergeRequest> mergeRequestIds;


    public DateScore(LocalDate date, double commitScore, String userName, Commit id) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
        this.numCommits = 1;
        this.commitIds = new ArrayList<>();
        this.mergeRequestIds = new ArrayList<MergeRequest>();
        commitIds.add(id);
    }

    public DateScore(LocalDate date, double score, String userName, Integer numMergeRequests, MergeRequest mergeRequestId) {
        this.date = date;
        this.mergeRequestScore = score;
        this.userName = userName;
        this.numMergeRequests = numMergeRequests;
        this.mergeRequestIds = new ArrayList<MergeRequest>();
        this.commitIds = new ArrayList<>();
        this.mergeRequestIds.add(mergeRequestId);
    }

    public DateScore(LocalDate date, String userName, Commit id) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
        this.numCommits = 1;
        this.commitIds = new ArrayList<>();
        this.mergeRequestIds = new ArrayList<MergeRequest>();
        commitIds.add(id);
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCommitScore(double commitScore) {
        this.commitScore = commitScore;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<Commit> getCommitIds() {
        return commitIds;
    }

    public void setCommitIds(List<Commit> commitIds) {
        this.commitIds = commitIds;
    }

    public void addCommitIds(Commit id) {
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

    public List<MergeRequest> getMergeRequestId() {
        return mergeRequestId;
    }

    public void setMergeRequestId(List<MergeRequest> mergeRequestId) {
        this.mergeRequestId = mergeRequestId;
    }

    public List<MergeRequest> getMergeRequestIds() {
        return mergeRequestIds;
    }

    public void setMergeRequestIds(ArrayList<MergeRequest> mergeRequestIds) {
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

    public void addMergeRequestIds(MergeRequest mergeRequest) {
        this.mergeRequestIds.add(mergeRequest);
    }

    @Override
    public String toString() {
        return "DateScore{" +
                "date=" + date +
                ", commitScore=" + commitScore +
                ", userName='" + userName + '\'' +
                ", numCommits=" + numCommits +
                '}';
    }
}
