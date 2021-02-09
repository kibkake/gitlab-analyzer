package DatabaseClasses.model.data;

import java.util.List;

public class Issue {

    private int PROJECT_ID;
    private int ISSUE_ID;
    private int ISSUE_IID;
    private String AUTHOR_NAME;
    private String TITLE;
    private int ISSUE_YEAR;
    private int ISSUE_MONTH;
    private int ISSUE_DAY;
    private List<Note> NOTES;

    public Issue() {
    }

    public Issue(int PROJECT_ID, int ISSUE_ID, int ISSUE_IID, String AUTHOR_NAME,
                 String TITLE, int ISSUE_YEAR, int ISSUE_MONTH, int ISSUE_DAY,
                 List<Note> NOTES) {
        this.PROJECT_ID = PROJECT_ID;
        this.ISSUE_ID = ISSUE_ID;
        this.ISSUE_IID = ISSUE_IID;
        this.AUTHOR_NAME = AUTHOR_NAME;
        this.TITLE = TITLE;
        this.ISSUE_YEAR = ISSUE_YEAR;
        this.ISSUE_MONTH = ISSUE_MONTH;
        this.ISSUE_DAY = ISSUE_DAY;
        this.NOTES = NOTES;
    }
}
