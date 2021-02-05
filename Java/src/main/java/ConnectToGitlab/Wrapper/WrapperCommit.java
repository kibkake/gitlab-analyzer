package main.java.ConnectToGitlab.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperCommit {

    private String id;
    private String authorName;
    private String authorEmail;
    private String title;
    private int commitYear;
    private int commitMonth;
    private int commitDay;
    private List<WrapperCommitDiff> wrapperCommitDiffs = new ArrayList<>();


    public WrapperCommit(String id, String authorName, String authorEmail, String title, int commitYear, int commitMonth, int commitDay) {
        this.id = id;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.title = title;
        this.commitYear = commitYear;
        this.commitMonth = commitMonth;
        this.commitDay = commitDay;
    }

    public String getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getTitle() {
        return title;
    }

    public int getCommitYear() {
        return commitYear;
    }

    public int getCommitMonth() {
        return commitMonth;
    }

    public int getCommitDay() {
        return commitDay;
    }

    public List<WrapperCommitDiff> getWrapperCommitDiffs() {
        return wrapperCommitDiffs;
    }
}
