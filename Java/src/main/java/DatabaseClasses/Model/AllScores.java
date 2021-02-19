package main.java.DatabaseClasses.Model;

import java.time.LocalDate;

public class AllScores {
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalCommitScore;
    private int totalCommentScore;
    private int totalMergeRequestScore;

    public AllScores() {

    }

    public AllScores(LocalDate startDate, LocalDate endDate, int totalCommitScore,
                     int totalCommentScore, int totalMergeRequestScore) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCommitScore = totalCommitScore;
        this.totalCommentScore = totalCommentScore;
        this.totalMergeRequestScore = totalMergeRequestScore;
    }
}
