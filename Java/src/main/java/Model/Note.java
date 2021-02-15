package main.java.Model;

import main.java.Model.Developer;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Note")
public class Note {

    private int id;
    private String body;
    private Developer author;
    private int score;
    private int year;
    private int month;
    private int day;
    private String username;
    private String created_at;
}
