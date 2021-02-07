package main.java.ConnectToGitlab.Wrapper;

public class WrapperNote {

    private final int ID;
    private final String BODY;
    private final String AUTHOR;
    private final int NAME;
    private final int SCORE;
    private final int YEAR;
    private final int MONTH;
    private final int DAY;

    public WrapperNote(int id, String body, String author, int name, int score, int year, int month, int day) {
        ID = id;
        this.BODY = body;
        this.AUTHOR = author;
        this.NAME = name;
        SCORE = score;
        YEAR = year;
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

    public int getName() {
        return NAME;
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
