package main.java.ConnectToGitlab.Wrapper;

import java.io.IOException;
import java.text.ParseException;

public class WrapperIssue {
    public static final String MAIN_URL = "https://cmpt373-1211-10.cmpt.sfu.ca/api/v4/projects";
    private final int PROJECT_ID;
    private final int ISSUE_ID;
    private final int ISSUE_IID;
    private final String AUTHOR_NAME;
    private final String TITLE;
    private final int ISSUE_YEAR;
    private final int ISSUE_MONTH;
    private final int ISSUE_DAY;

    public WrapperIssue(String token, int projectId, int issueId, int issueIid, String authorName, String title,
                        int year, int month, int day) throws IOException, ParseException {
        this.PROJECT_ID = projectId;
        this.ISSUE_ID = issueId;
        this.ISSUE_IID = issueIid;
        this.AUTHOR_NAME = authorName;
        this.TITLE = title;
        this.ISSUE_YEAR = year;
        this.ISSUE_MONTH = month;
        this.ISSUE_DAY = day;
        //getIssueNotes(token);
    }

    public int getProjectId() {
        return PROJECT_ID;
    }

    public int getIssueId() {
        return ISSUE_ID;
    }

    public int getIssueIid() {
        return ISSUE_IID;
    }

    public String getAuthorName() {
        return AUTHOR_NAME;
    }

    public String getTitle() {
        return TITLE;
    }

    public int getIssueYear() {
        return ISSUE_YEAR;
    }

    public int getIssueMonth() {
        return ISSUE_MONTH;
    }

    public int getIssueDay() {
        return ISSUE_DAY;
    }
}
