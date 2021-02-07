package main.java.ConnectToGitlab.Wrapper;

public class WrapperIssue {

    private final int PROJECT_ID;
    private final int ISSUE_ID;
    private final int ISSUE_IID;
    private final String AUTHOR_NAME;
    private final String TITLE;

    public WrapperIssue(int projectId, int issueId, int issueIid, String authorName, String title) {
        this.PROJECT_ID = projectId;
        this.ISSUE_ID = issueId;
        this.ISSUE_IID = issueIid;
        this.AUTHOR_NAME = authorName;
        this.TITLE = title;
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
}
