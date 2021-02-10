package main.java.DatabaseClasses.Model.data;

import java.util.List;

public class MergedRequest {

    private int MERGE_REQUEST_ID;
    private int MERGE_REQUEST_IID;
    private int PROJECT_ID;
    private String MERGE_REQUEST_TITLE;
    private int MERGE_YEAR;
    private int MERGE_MONTH;
    private int MERGE_DAY;
    private double MERGE_SCORE;
    private List<Commits> MERGE_REQUEST_COMMITS;
    private List<CommitDiff> MERGE_DIFFS;
    private List<Note> NOTES;

    public MergedRequest() {
    }

    public MergedRequest(int MERGE_REQUEST_ID, int MERGE_REQUEST_IID, int PROJECT_ID,
                         String MERGE_REQUEST_TITLE, int MERGE_YEAR, int MERGE_MONTH, int MERGE_DAY,
                         double MERGE_SCORE, List<Commits> MERGE_REQUEST_COMMITS, List<CommitDiff> MERGE_DIFFS,
                         List<Note> NOTES) {
        this.MERGE_REQUEST_ID = MERGE_REQUEST_ID;
        this.MERGE_REQUEST_IID = MERGE_REQUEST_IID;
        this.PROJECT_ID = PROJECT_ID;
        this.MERGE_REQUEST_TITLE = MERGE_REQUEST_TITLE;
        this.MERGE_YEAR = MERGE_YEAR;
        this.MERGE_MONTH = MERGE_MONTH;
        this.MERGE_DAY = MERGE_DAY;
        this.MERGE_SCORE = MERGE_SCORE;
        this.MERGE_REQUEST_COMMITS = MERGE_REQUEST_COMMITS;
        this.MERGE_DIFFS = MERGE_DIFFS;
        this.NOTES = NOTES;
    }
}
