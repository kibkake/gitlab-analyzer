package DatabaseClasses.model.data;

import org.springframework.data.annotation.Id;

public class Member {

    @Id
    private int memberId;
    private String memberName;

    private int scoreOnMergeRequest;
    private int numMergeRequest;
    private int scoreCommit;
    private int numCommit;
    private int numWordsOnNotesForMR;
    private int numWordsOnNotesForIssue;

    // I guess per day results should be calculated as the user selects the range of date?

    public Member(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public Member(int memberId, String memberName, int scoreOnMergeRequest,
                  int numMergeRequest, int scoreCommit, int numCommit,
                  int numWordsOnNotesForMR, int numWordsOnNotesForIssue) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.scoreOnMergeRequest = scoreOnMergeRequest;
        this.numMergeRequest = numMergeRequest;
        this.scoreCommit = scoreCommit;
        this.numCommit = numCommit;
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
    }

    public void setScoreOnMergeRequest(int scoreOnMergeRequest) {
        this.scoreOnMergeRequest = scoreOnMergeRequest;
    }

    public void setNumMergeRequest(int numMergeRequest) {
        this.numMergeRequest = numMergeRequest;
    }

    public void setScoreCommit(int scoreCommit) {
        this.scoreCommit = scoreCommit;
    }

    public void setNumCommit(int numCommit) {
        this.numCommit = numCommit;
    }

    public void setNumWordsOnNotesForMR(int numWordsOnNotesForMR) {
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
    }

    public void setNumWordsOnNotesForIssue(int numWordsOnNotesForIssue) {
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
    }
}
