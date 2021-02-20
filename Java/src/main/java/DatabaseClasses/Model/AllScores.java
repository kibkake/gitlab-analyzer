package main.java.DatabaseClasses.Model;

import java.time.LocalDate;
/**
 * This class stores a user's total commit score, total comment score, and total merge request score
 * over a given time period (from startDate to endDate).
 */
public class AllScores {
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCommitScore;
    private double totalCommentScore;
    private double totalMergeRequestScore;

    public AllScores() {

    }

    public AllScores(LocalDate startDate, LocalDate endDate, double totalCommitScore,
                     double totalCommentScore, double totalMergeRequestScore) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCommitScore = totalCommitScore;
        this.totalCommentScore = totalCommentScore;
        this.totalMergeRequestScore = totalMergeRequestScore;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalCommentScore() {
        return totalCommentScore;
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

    public void setTotalCommentScore(double totalCommentScore) {
        this.totalCommentScore = totalCommentScore;
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
                ", totalCommentScore='" + totalCommentScore +
                ", totalMergeRequestScore=" + totalMergeRequestScore +
                '}';
    }
}
