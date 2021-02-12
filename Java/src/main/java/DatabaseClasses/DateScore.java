package main.java.DatabaseClasses;

import java.time.LocalDate;

/**
 *  This is a simple class that stores two attributes: a date, the commit score on that date, and
 *  the name of the user this is for.
 */
public class DateScore {
    private LocalDate date;
    private int commitScore;
    private String userName;

    public DateScore(LocalDate date, int commitScore, String userName) {
        this.date = date;
        this.commitScore = commitScore;
        this.userName = userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCommitScore() {
        return commitScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCommitScore(int commitScore) {
        this.commitScore = commitScore;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
