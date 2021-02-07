package DatabaseClasses.model.data;

import java.util.List;

public class NoteOnIssue {
    private int noteOnIssueId;
    private String author_userName;
    private String created_at;
    private String body;

    public NoteOnIssue(int noteOnIssueId, String author_userName,
                       String created_at, String body) {
        this.noteOnIssueId = noteOnIssueId;
        this.author_userName = author_userName;
        this.created_at = created_at;
        this.body = body;
    }

    public void setNoteOnIssueId(int noteOnIssueId) {
        this.noteOnIssueId = noteOnIssueId;
    }

    public void setAuthor_userName(String author_userName) {
        this.author_userName = author_userName;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
