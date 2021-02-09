package DatabaseClasses.model.data;

import DatabaseClasses.model.data.Issue;
import DatabaseClasses.model.data.MergedRequest;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * This class contains calculation results to be sent to frontend as well as member id information.
 * ( This is the where Abtin's wrapper classes and this structure are different.
 *   The details should be consulted in detail.)
 */
public class Member {

    private String NAME;
    private List<MergedRequest> MERGED_MERGE_REQUESTS;
    private List<Issue> ALL_ISSUES;
    private double commitScore;
    private double issueScore;

    // variables to get by calculation
    private int scoreOnMergeRequest;
    private int numMergeRequest;
    private int numCommit;
    private int numMergeRequestPerDay;
    private int numCommitPerDay;
    private int numWordsOnNotesForMR;
    private int numWordsOnNotesForIssue;
    private int notesOnMergeRequestPerDay;
    private int notesOnIssuePerDay;


    public Member() {
    }

    public Member(String NAME) {
        this.NAME = NAME;
    }

    public Member(String NAME, List<MergedRequest> MERGED_MERGE_REQUESTS,
                  List<Issue> ALL_ISSUES, double commitScore, double issueScore) {
        this.NAME = NAME;
        this.MERGED_MERGE_REQUESTS = MERGED_MERGE_REQUESTS;
        this.ALL_ISSUES = ALL_ISSUES;
        this.commitScore = commitScore;
        this.issueScore = issueScore;
    }

    public Member(String NAME, List<MergedRequest> MERGED_MERGE_REQUESTS, List<Issue> ALL_ISSUES,
                  double commitScore, double issueScore, int scoreOnMergeRequest, int numMergeRequest,
                  int numCommit, int numMergeRequestPerDay, int numCommitPerDay, int numWordsOnNotesForMR,
                  int numWordsOnNotesForIssue, int notesOnMergeRequestPerDay, int notesOnIssuePerDay) {
        this.NAME = NAME;
        this.MERGED_MERGE_REQUESTS = MERGED_MERGE_REQUESTS;
        this.ALL_ISSUES = ALL_ISSUES;
        this.commitScore = commitScore;
        this.issueScore = issueScore;
        this.scoreOnMergeRequest = scoreOnMergeRequest;
        this.numMergeRequest = numMergeRequest;
        this.numCommit = numCommit;
        this.numMergeRequestPerDay = numMergeRequestPerDay;
        this.numCommitPerDay = numCommitPerDay;
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
        this.notesOnMergeRequestPerDay = notesOnMergeRequestPerDay;
        this.notesOnIssuePerDay = notesOnIssuePerDay;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public void setCommitScore(double commitScore) {
        this.commitScore = commitScore;
    }

    public double getIssueScore() {
        return issueScore;
    }

    public void setIssueScore(double issueScore) {
        this.issueScore = issueScore;
    }

    public int getScoreOnMergeRequest() {
        return scoreOnMergeRequest;
    }

    public void setScoreOnMergeRequest(int scoreOnMergeRequest) {
        this.scoreOnMergeRequest = scoreOnMergeRequest;
    }

    public int getNumMergeRequest() {
        return numMergeRequest;
    }

    public void setNumMergeRequest(int numMergeRequest) {
        this.numMergeRequest = numMergeRequest;
    }

    public int getNumCommit() {
        return numCommit;
    }

    public void setNumCommit(int numCommit) {
        this.numCommit = numCommit;
    }

    public int getNumMergeRequestPerDay() {
        return numMergeRequestPerDay;
    }

    public void setNumMergeRequestPerDay(int numMergeRequestPerDay) {
        this.numMergeRequestPerDay = numMergeRequestPerDay;
    }

    public int getNumCommitPerDay() {
        return numCommitPerDay;
    }

    public void setNumCommitPerDay(int numCommitPerDay) {
        this.numCommitPerDay = numCommitPerDay;
    }

    public int getNumWordsOnNotesForMR() {
        return numWordsOnNotesForMR;
    }

    public void setNumWordsOnNotesForMR(int numWordsOnNotesForMR) {
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
    }

    public int getNumWordsOnNotesForIssue() {
        return numWordsOnNotesForIssue;
    }

    public void setNumWordsOnNotesForIssue(int numWordsOnNotesForIssue) {
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
    }

    public int getNotesOnMergeRequestPerDay() {
        return notesOnMergeRequestPerDay;
    }

    public void setNotesOnMergeRequestPerDay(int notesOnMergeRequestPerDay) {
        this.notesOnMergeRequestPerDay = notesOnMergeRequestPerDay;
    }

    public int getNotesOnIssuePerDay() {
        return notesOnIssuePerDay;
    }

    public void setNotesOnIssuePerDay(int notesOnIssuePerDay) {
        this.notesOnIssuePerDay = notesOnIssuePerDay;
    }
}
