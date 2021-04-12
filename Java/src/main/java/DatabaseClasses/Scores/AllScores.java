package main.java.DatabaseClasses.Scores;

import java.time.LocalDateTime;
/**
 * This class stores a user's total commit score, total comment score, and total merge request score
 * over a given time period (from startDate to endDate).
 */
public class AllScores {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalCommitScore;
    private int totalCommentWordCount;
    private Double totalMergeRequestScore;

    public AllScores() {

    }

    public AllScores(LocalDateTime startDate, LocalDateTime endDate, double totalCommitScore,
                     int totalCommentWordCount, double totalMergeRequestScore) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCommitScore = totalCommitScore;
        this.totalCommentWordCount = totalCommentWordCount;
        this.totalMergeRequestScore = totalMergeRequestScore;
    }

    public AllScores(LocalDateTime startDate, LocalDateTime endDate, Double totalCommitScore, Double totalMergeRequestScore) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCommitScore = totalCommitScore;
        this.totalMergeRequestScore = totalMergeRequestScore;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getTotalCommentWordCount() {
        return totalCommentWordCount;
    }

    public double getTotalMergeRequestScore() {
        return totalMergeRequestScore;
    }

    public void setTotalCommitScore(double totalCommitScore) {
        this.totalCommitScore = totalCommitScore;
    }

    public void setTotalMergeRequestScore(double totalMergeRequestScore) {
        this.totalMergeRequestScore = totalMergeRequestScore;
    }

    public void setTotalCommentWordCount(int totalCommentWordCount) {
        this.totalCommentWordCount = totalCommentWordCount;
    }

    public double getTotalCommitScore() {
        return totalCommitScore;
    }

    @Override
    public String toString() {
        return "AllScores{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCommitScore=" + totalCommitScore +
                ", totalCommentWordCount='" + totalCommentWordCount +
                ", totalMergeRequestScore=" + totalMergeRequestScore +
                '}';
    }
}
