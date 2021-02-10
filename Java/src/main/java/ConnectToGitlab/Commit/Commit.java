package main.java.ConnectToGitlab.Commit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

import main.java.ConnectToGitlab.Wrapper.WrapperCommitDiff;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.stream.Collectors;

/**
 *  Holds import information about commits and is used to convert JSON to an object with spring
 */
@Document(collection = "commits")
public class Commit {

    @Id
    private String sha;
    private String id;
    private String shortId;
    private String created_at;
    private ArrayList<String> parent_ids;
    private String title; // 1st line of commit message
    private String message; // other lines of commit message
    private String author_name;
    private String author_email;
    private String authored_date;
    // TODO find difference between committer and author
    private String committer_name;
    private String committer_email;
    private String committed_date;
    private String web_url;
    //holds add, delete and total changes of a single commit
    private Stats stats;
    private List<CommitDiffs> diffs;
    private Date date;

//         URL url = new URL(MAIN_URL + "/" + projectId + "/repository/commits/" + commitHash + "/" + "diff" + "?access_token=" + token);

    public Commit() {
    }

    public Commit(String id, String shortId, String created_at, ArrayList<String> parent_ids, String title,
                  String message, String author_name, String author_email, String authored_date, String committer_name,
                  String committer_email, String committed_date, String web_url, Stats stats) {
        this.id = id;
        this.shortId = shortId;
        this.created_at = created_at;
        this.parent_ids = parent_ids;
        this.title = title;
        this.message = message;
        this.author_name = author_name;
        this.author_email = author_email;
        this.authored_date = authored_date;
        this.committer_name = committer_name;
        this.committer_email = committer_email;
        this.committed_date = committed_date;
        this.web_url = web_url;
        this.stats = stats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<String> getParent_ids() {
        return parent_ids;
    }

    public void setParent_ids(ArrayList<String> parent_ids) {
        this.parent_ids = parent_ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getCommitter_name() {
        return committer_name;
    }

    public void setCommitter_name(String committer_name) {
        this.committer_name = committer_name;
    }

    public String getCommitter_email() {
        return committer_email;
    }

    public void setCommitter_email(String committer_email) {
        this.committer_email = committer_email;
    }

    public String getCommitted_date() {
        return committed_date;
    }

    public void setCommitted_date(String committed_date) {
        this.committed_date = committed_date;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public double getCommitScore() {
        int additions = stats.getAdditions();
        int deletions = stats.getDeletions();
        double score = additions + (deletions * 0.2);
        return score;
    }

    public List<CommitDiffs> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<CommitDiffs> diffs) {
        this.diffs = diffs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        if(date == null) {
            OffsetDateTime dateWithOffSet = OffsetDateTime.parse(committed_date);
            this.date = Date.from(dateWithOffSet.toInstant());
        }
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", shortId='" + shortId + '\'' +
                ", created_at=" + created_at +
                ", parent_ids=" + parent_ids +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_email='" + author_email + '\'' +
                ", authored_date=" + authored_date +
                ", committer_name='" + committer_name + '\'' +
                ", committer_email='" + committer_email + '\'' +
                ", committed_date='" + committed_date + '\'' +
                ", web_url='" + web_url + '\'' +
                ", web_url='" + stats + '\'' +
                '}';
    }

}
