package main.java.ConnectToGitlab.Wrapper;

/**
 * This class includes all of the important data about a repository note. These
 * notes are posts on an issue or merge request. They have an author and score,
 * which comes from the number of words in the body.
 */
public class WrapperNote {

    private int ID;
    private String body;
    private String author;
    private int score;
    private int year;
    private int month;
    private int day;

    public WrapperNote() {
    }

    public WrapperNote(int id, String body, String author, int year, int month, int day) {
        this.ID = id;
        this.body = body;
        this.author = author;
        this.score = body.length();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getId() {
        return ID;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public int getScore() {
        return score;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
