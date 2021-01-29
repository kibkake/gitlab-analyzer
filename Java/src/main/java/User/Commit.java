package User;

import java.time.LocalDate;

public class Commit {
    private String id;
    private String shortId;
    private String title; // 1st line of commit message
    private String message; // other lines of commit message
    private String author_name;
    private LocalDate authored_date;
    // TODO find difference between committer and author
    private String committer_name;
    private String committer_email;
    private String committed_date;
    private LocalDate created_at;

    // get these from stats
    private int additions;
    private int deletions;
    private int totalChanges;

    public Commit(String id, String shortId, String title, String message, String author_name, LocalDate authored_date,
                  String committer_name, String committer_email, String committed_date, LocalDate created_at) {
        this.id = id;
        this.shortId = shortId;
        this.title = title;
        this.message = message;
        this.author_name = author_name;
        this.authored_date = authored_date;
        this.committer_name = committer_name;
        this.committer_email = committer_email;
        this.committed_date = committed_date;
        this.created_at = created_at;
    }
}
