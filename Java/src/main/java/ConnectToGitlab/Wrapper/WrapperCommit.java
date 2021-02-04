package main.java.ConnectToGitlab.Wrapper;

public class WrapperCommit {

    private String id;
    private String authorName;
    private String authorEmail;
    private String title;
    private int commitYear;
    private int commitMonth;
    private int commitDay;

    //private List<commitdiffs>


    public WrapperCommit(String id, String authorName, String authorEmail, String title, int commitYear, int commitMonth, int commitDay) {
        this.id = id;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.title = title;
        this.commitYear = commitYear;
        this.commitMonth = commitMonth;
        this.commitDay = commitDay;
    }
}
