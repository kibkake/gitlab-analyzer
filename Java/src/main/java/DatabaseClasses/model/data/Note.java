package main.java.DatabaseClasses.model.data;

import java.util.List;

public class Note {
    private int ID;
    private String BODY;
    private String AUTHOR;
    private int SCORE;
    private int YEAR;
    private int MONTH;
    private int DAY;

    public Note(int id, String body, String author, int year, int month, int day) {
        this.ID = id;
        this.BODY = body;
        this.AUTHOR = author;
        this.SCORE = body.length();
        this.YEAR = year;
        this.MONTH = month;
        this.DAY = day;
    }


}
