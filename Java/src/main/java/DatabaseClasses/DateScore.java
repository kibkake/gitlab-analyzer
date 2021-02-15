package main.java.DatabaseClasses;

import java.time.LocalDate;

/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class DateScore {
    private LocalDate date;
    private double commitScore;
    private String userName;
    private int numCommits;

    public DateScore(LocalDate date, double commitScore, String userName) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
        this.numCommits = 1;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCommitScore() {
        return commitScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCommitScore(double commitScore) {
        this.commitScore = commitScore;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addToScore(double score) {
        this.commitScore += score;
    }

    public int getNumCommits() {
        return numCommits;
    }

    public void setNumCommits(int numCommits) {
        this.numCommits = numCommits;
    }

    public void incrementNumberOfCommitsBy1() {
        this.numCommits = this.numCommits + 1;
    }

    @Override
    public String toString() {
        return "DateScore{" +
                "date=" + date +
                ", commitScore=" + commitScore +
                ", userName='" + userName + '\'' +
                ", numCommits=" + numCommits +
                '}';
    }
}
