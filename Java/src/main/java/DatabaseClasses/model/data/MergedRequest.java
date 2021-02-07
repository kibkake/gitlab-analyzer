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
}
