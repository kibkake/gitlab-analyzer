package main.java.DatabaseClasses.Scores;

import java.time.LocalDate;
import java.util.List;

public class MergeRequestDateScore {

    private LocalDate mergedDate;
    private double mergeRequestScore;
    private int numMergeRequests;
    private List<Integer> mergeRequestId;

    public MergeRequestDateScore() {
    }

    public LocalDate getMergedDate() {
        return mergedDate;
    }

    public void setMergedDate(LocalDate mergedDate) {
        this.mergedDate = mergedDate;
    }

    public double getMergeRequestScore() {
        return mergeRequestScore;
    }

    public void setMergeRequestScore(double mergeRequestScore) {
        this.mergeRequestScore = mergeRequestScore;
    }

    public int getNumMergeRequests() {
        return numMergeRequests;
    }

    public void setNumMergeRequests(int numMergeRequests) {
        this.numMergeRequests = numMergeRequests;
    }

    public List<Integer> getMergeRequestId() {
        return mergeRequestId;
    }

    public void setMergeRequestId(List<Integer> mergeRequestId) {
        this.mergeRequestId = mergeRequestId;
    }

    public void addToMergeRequestScore(Double score) {
        this.mergeRequestScore = this.mergeRequestScore + score;
    }

    public void incrementNumMergeRequests() {
        this.numMergeRequests = this.numMergeRequests + 1;
    }


    @Override
    public String toString() {
        return "DateScore{" +
                "date=" + mergedDate +
                '}';
    }
}
