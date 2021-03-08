package main.java.Model;

import main.java.DatabaseClasses.Model.CommitDateScore;
import main.java.DatabaseClasses.Model.MergeRequestDateScore;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 *  Class hold information about the different users on gitlab used to convert JSON to an object with spring
 *  We call this class developer to not get confused between the user of our product and gitlab users. So we
 *  call gitlab users developers
 */
@Document(value = "Developer")
public class Developer {
    private int id;
    private String name;
    private String username;
    private List<String> emails;
    private List<CommitDateScore> commitDateScores;
    private List<MergeRequestDateScore> mergeRequestDateScores;
    private List<MergeRequest> mergeRequests;

    public Developer() {
    }

    public Developer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CommitDateScore> getCommitDateScores() {
        return commitDateScores;
    }

    public void setCommitDateScores(List<CommitDateScore> commitDateScores) {
        this.commitDateScores = commitDateScores;
    }

    public List<MergeRequestDateScore> getMergeRequestDateScores() {
        return mergeRequestDateScores;
    }

    public void setMergeRequestDateScores(List<MergeRequestDateScore> mergeRequestDateScores) {
        this.mergeRequestDateScores = mergeRequestDateScores;
    }

    public List<MergeRequest> getMergeRequests() {
        return mergeRequests;
    }

    public void setMergeRequests(List<MergeRequest> mergeRequests) {
        this.mergeRequests = mergeRequests;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
