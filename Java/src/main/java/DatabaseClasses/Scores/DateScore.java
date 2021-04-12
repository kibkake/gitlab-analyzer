package main.java.DatabaseClasses.Scores;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.Collections.*;
/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class DateScore {
    private LocalDateTime date;
    private double commitScore;
    private double mergeRequestScore;
    private String userName;
    private int numCommits;
    private int numMergeRequests;
    private List<Integer> mergeRequestId;
    private List<DateScoreDiff> commitDiffs;
    private ArrayList<DateScoreDiff> mergeRequestDiffs;
    private double sumOfCommits;

    public double getSumOfCommits() {
        return sumOfCommits;
    }

    public void setSumOfCommits(double sumOfCommits) {
        this.sumOfCommits = sumOfCommits;
    }

    public DateScore(LocalDateTime date, double commitScore, String userName, List<Diff> commit) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
        this.numCommits = 1;
        this.commitDiffs = new ArrayList<>();
        this.mergeRequestDiffs = new ArrayList<DateScoreDiff>();
        for(Diff d :commit){
            DateScoreDiff diff = new DateScoreDiff(d);
            this.commitDiffs.add(diff);
        }
    }

    public DateScore(LocalDateTime date, double score, String userName, Integer numMergeRequests, List<Diff> mergeRequest, double sumOfCommits) {
        this.date = date;
        this.mergeRequestScore = score;
        this.userName = userName;
        this.numMergeRequests = numMergeRequests;
        this.mergeRequestDiffs = new ArrayList<DateScoreDiff>();
        this.commitDiffs = new ArrayList<>();
        for(Diff d :mergeRequest){
            DateScoreDiff diff = new DateScoreDiff(d);
            this.mergeRequestDiffs.add(diff);
        }
        this.sumOfCommits = sumOfCommits;
    }

    public DateScore(LocalDateTime date, String userName, DateScoreDiff commitDiffScore) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
        this.numCommits = 1;
        this.commitDiffs = new ArrayList<>();
        this.mergeRequestDiffs = new ArrayList<DateScoreDiff>();
        commitDiffs.add(commitDiffScore);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(LocalDateTime date) {
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

    public List<DateScoreDiff> getCommitDiffs() {
        return commitDiffs;
    }

    public void setCommitDiffs(List<DateScoreDiff> commitDiffs) {
        this.commitDiffs = commitDiffs;
    }

    public void addCommitDiffs(Commit commit) {
        for(Diff d :commit.getDiffs()){
            DateScoreDiff diff = new DateScoreDiff(d);
            this.commitDiffs.add(diff);
        }
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

    public List<DateScoreDiff> getMergeRequestDiffs() {
        return mergeRequestDiffs;
    }

    public void setMergeRequestDiffs(ArrayList<DateScoreDiff> mergeRequestDiff) {
        this.mergeRequestDiffs = mergeRequestDiff;
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

    public void addMergeRequestDiffs(MergeRequest mergeRequest) {
        for(Diff d :mergeRequest.getDiffs()){
            DateScoreDiff diff = new DateScoreDiff(d);
            this.mergeRequestDiffs.add(diff);
        }
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
