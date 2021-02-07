package DatabaseClasses.model.data;

import java.util.List;

public class MergedRequest {

    private int mergeRequestId;
    private String mergeRequestTitle;
    private int created_at;
    private List<Commits> mergeRequestCommitsList;
    private List<NoteOnMergeRequest> noteOnMergeRequestsList;
    private int numWords;

    public MergedRequest(int mergeRequestId, String mergeRequestTitle, int created_at,
                         List<Commits> mergeRequestCommitsList, List<NoteOnMergeRequest> noteOnMergeRequestsList,
                         int numWords) {
        this.mergeRequestId = mergeRequestId;
        this.mergeRequestTitle = mergeRequestTitle;
        this.created_at = created_at;
        this.mergeRequestCommitsList = mergeRequestCommitsList;
        this.noteOnMergeRequestsList = noteOnMergeRequestsList;
        this.numWords = numWords;
    }

    public void setMergeRequestId(int mergeRequestId) {
        this.mergeRequestId = mergeRequestId;
    }

    public void setMergeRequestTitle(String mergeRequestTitle) {
        this.mergeRequestTitle = mergeRequestTitle;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public void setMergeRequestCommitsList(List<Commits> mergeRequestCommitsList) {
        this.mergeRequestCommitsList = mergeRequestCommitsList;
    }

    public void setNoteOnMergeRequestsList(List<NoteOnMergeRequest> noteOnMergeRequestsList) {
        this.noteOnMergeRequestsList = noteOnMergeRequestsList;
    }

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }
}
