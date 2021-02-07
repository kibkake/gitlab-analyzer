package DatabaseClasses.model.data;

import java.util.List;

public class Issue {
    public Issue(int issueId, boolean isOpened, List<NoteOnIssue> noteOnIssueList) {
        this.issueId = issueId;
        this.isOpened = isOpened;
        this.noteOnIssueList = noteOnIssueList;
    }

    private int issueId;
    private boolean isOpened;
    private List<NoteOnIssue> noteOnIssueList;
}
