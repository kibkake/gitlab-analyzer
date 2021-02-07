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
}
