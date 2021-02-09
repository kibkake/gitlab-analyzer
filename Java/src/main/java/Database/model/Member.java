package Database.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Member")
public class Member {

    @Id
    private int memberId;
    private String memberName;

    private int scoreOnMergeRequest;
    private int numMergeRequest;
    private int scoreCommit;
    private int numCommit;
    private int numMergeRequestPerDay;
    private int numCommitPerDay;
    private int numWordsOnNotesForMR;
    private int numWordsOnNotesForIssue;
    private int notesOnMergeRequestPerDay;
    private int notesOnIssuePerDay;

    public Member(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public Member(int memberId, String memberName, int scoreOnMergeRequest, int numMergeRequest,
                  int scoreCommit, int numCommit, int numMergeRequestPerDay, int numCommitPerDay,
                  int numWordsOnNotesForMR, int numWordsOnNotesForIssue,
                  int notesOnMergeRequestPerDay, int notesOnIssuePerDay) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.scoreOnMergeRequest = scoreOnMergeRequest;
        this.numMergeRequest = numMergeRequest;
        this.scoreCommit = scoreCommit;
        this.numCommit = numCommit;
        this.numMergeRequestPerDay = numMergeRequestPerDay;
        this.numCommitPerDay = numCommitPerDay;
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
        this.notesOnMergeRequestPerDay = notesOnMergeRequestPerDay;
        this.notesOnIssuePerDay = notesOnIssuePerDay;
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

    public void setNumMergeRequestPerDay(int numMergeRequestPerDay) {
        this.numMergeRequestPerDay = numMergeRequestPerDay;
    }

    public void setNumCommitPerDay(int numCommitPerDay) {
        this.numCommitPerDay = numCommitPerDay;
    }

    public void setNumWordsOnNotesForMR(int numWordsOnNotesForMR) {
        this.numWordsOnNotesForMR = numWordsOnNotesForMR;
    }

    public void setNumWordsOnNotesForIssue(int numWordsOnNotesForIssue) {
        this.numWordsOnNotesForIssue = numWordsOnNotesForIssue;
    }

    public void setNotesOnMergeRequestPerDay(int notesOnMergeRequestPerDay) {
        this.notesOnMergeRequestPerDay = notesOnMergeRequestPerDay;
    }

    public void setNotesOnIssuePerDay(int notesOnIssuePerDay) {
        this.notesOnIssuePerDay = notesOnIssuePerDay;
    }
}
