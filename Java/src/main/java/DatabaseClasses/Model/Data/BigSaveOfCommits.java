package main.java.DatabaseClasses.Model.Data;

import main.java.DatabaseClasses.Model.Data.CommitDiff;
import java.util.List;

public class BigSaveOfCommits {

    private String ID;
    private String AUTHOR_NAME;
    private String AUTHOR_EMAIL;
    private String TITLE;
    private int COMMIT_YEAR;
    private int COMMIT_MONTH;
    private int COMMIT_DAY;
    private double commitScore;
    private List<CommitDiff> COMMIT_DIFFS;

    public BigSaveOfCommits() {
    }

    public BigSaveOfCommits(String ID, String AUTHOR_NAME, String AUTHOR_EMAIL, String TITLE,
                            int COMMIT_YEAR, int COMMIT_MONTH, int COMMIT_DAY,
                            double commitScore, List<CommitDiff> COMMIT_DIFFS) {
        this.ID = ID;
        this.AUTHOR_NAME = AUTHOR_NAME;
        this.AUTHOR_EMAIL = AUTHOR_EMAIL;
        this.TITLE = TITLE;
        this.COMMIT_YEAR = COMMIT_YEAR;
        this.COMMIT_MONTH = COMMIT_MONTH;
        this.COMMIT_DAY = COMMIT_DAY;
        this.commitScore = commitScore;
        this.COMMIT_DIFFS = COMMIT_DIFFS;
    }
}
