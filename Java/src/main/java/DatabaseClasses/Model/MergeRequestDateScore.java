package main.java.DatabaseClasses.Model;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MergeRequestDateScore {
    private LocalDate date;
    private double score;
    private String userName;
    private int numMergeRequests;
    private List<Integer> mergeRequestIds;
    private List<String> commitIds;

    public MergeRequestDateScore(LocalDate date, double score, String userName, Integer numMergeRequests, Integer mergeRequestId,
                                 List<String> commitIds) {
        this.date = date;
        this.score = score;
        this.userName = userName;
        this.numMergeRequests = numMergeRequests;
        this.mergeRequestIds = new ArrayList<Integer>();
        this.mergeRequestIds.add(mergeRequestId);
        this.commitIds = commitIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumMergeRequests() {
        return numMergeRequests;
    }

    public void setNumMergeRequests(int numMergeRequests) {
        this.numMergeRequests = numMergeRequests;
    }

    public List<Integer> getMergeRequestIds() {
        return mergeRequestIds;
    }

    public void setMergeRequestIds(List<Integer> mergeRequestIds) {
        this.mergeRequestIds = mergeRequestIds;
    }

    public List<String> getCommitIds() {
        return commitIds;
    }

    public void setCommitIds(List<String> commitIds) {
        this.commitIds = commitIds;
    }

    public void addToScore(Double score) {
        this.score = this.score + score;
    }

    public void incrementNumMergeRequests() {
        this.numMergeRequests = this.numMergeRequests + 1;
    }

    public void addMergeRequestIds(int id) {
        this.mergeRequestIds.add(id);
    }

    public void addCommitIds(List<String> ids) {
        this.commitIds.addAll(ids);
    }


}
