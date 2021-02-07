package DatabaseClasses.model.data;

public class NoteOnMergeRequest {
    private int noteOnMergeRequestId;
    private String author_userName;
    private String created_at;
    private String body;

    public NoteOnMergeRequest(int noteOnMergeRequestId, String author_userName,
                              String created_at, String body) {
        this.noteOnMergeRequestId = noteOnMergeRequestId;
        this.author_userName = author_userName;
        this.created_at = created_at;
        this.body = body;
    }

    public void setNoteOnMergeRequestId(int noteOnMergeRequestId) {
        this.noteOnMergeRequestId = noteOnMergeRequestId;
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
