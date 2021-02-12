package main.java.DatabaseClasses.Model.Data;

public class CommitDiff {

    private String NEW_PATH;
    private String OLD_PATH;
    private boolean NEW_FILE;
    private boolean RENAMED_FILE;
    private boolean DELETED_FILE;
    private String DIFF;
    private double SCORE;

    public CommitDiff() {
    }

    public CommitDiff(String NEW_PATH, String OLD_PATH, boolean NEW_FILE,
                      boolean RENAMED_FILE, boolean DELETED_FILE, String DIFF, double SCORE) {
        this.NEW_PATH = NEW_PATH;
        this.OLD_PATH = OLD_PATH;
        this.NEW_FILE = NEW_FILE;
        this.RENAMED_FILE = RENAMED_FILE;
        this.DELETED_FILE = DELETED_FILE;
        this.DIFF = DIFF;
        this.SCORE = SCORE;
    }
}
