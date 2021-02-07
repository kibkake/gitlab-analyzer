package main.java.ConnectToGitlab.Wrapper;

public class WrapperIssue {

    private final int PROJECT_ID;
    private final int ISSUE_ID;
    private final int ISSUE_IID;
    private final int AUTHOR_NAME;

    public WrapperIssue(int PROJECT_ID, int ISSUE_ID, int ISSUE_IID, int AUTHOR_NAME) {
        this.PROJECT_ID = PROJECT_ID;
        this.ISSUE_ID = ISSUE_ID;
        this.ISSUE_IID = ISSUE_IID;
        this.AUTHOR_NAME = AUTHOR_NAME;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public int getISSUE_ID() {
        return ISSUE_ID;
    }

    public int getISSUE_IID() {
        return ISSUE_IID;
    }

    public int getAUTHOR_NAME() {
        return AUTHOR_NAME;
    }
}
