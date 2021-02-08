package main.java.ConnectToGitlab.Wrapper;

/**
 * This class includes all of the important data about a repository note. These
 * notes are posts on an issue or merge request. They have an author and score,
 * which comes from the number of words in the body.
 */
public class WrapperNote {

    private final int ID;
    private final String BODY;
    private final String AUTHOR;
    private final int SCORE;
    private final int YEAR;
    private final int MONTH;
    private final int DAY;

    public WrapperNote(int id, String body, String author, int year, int month, int day) {
        this.ID = id;
        this.BODY = body;
        this.AUTHOR = author;
        this.SCORE = body.length();
        this.YEAR = year;
        this.MONTH = month;
        this.DAY = day;
    }

    public int getId() {
        return ID;
    }

    public String getBody() {
        return BODY;
    }

    public String getAuthor() {
        return AUTHOR;
    }

    public int getScore() {
        return SCORE;
    }

    public int getYear() {
        return YEAR;
    }

    public int getMonth() {
        return MONTH;
    }

    public int getDay() {
        return DAY;
    }
}
