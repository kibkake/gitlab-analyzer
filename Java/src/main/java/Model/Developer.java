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
    private int devId;

    /* a Dev can belong to many projects but should only be scored for the project in question so we will use project id
      to link them to there work in a specific project */
    private int projectId;

    private String name;
    private String username;
    private List<String> emails;
    private List<CommitDateScore> commitDateScores;
    private List<MergeRequestDateScore> mergeRequestDateScores;
    private List<MergeRequest> mergeRequestsAndCommits;

    public Developer() {
    }

    public Developer(int devId) {
        this.devId = devId;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
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

    public List<MergeRequest> getMergeRequestsAndCommits() {
        return mergeRequestsAndCommits;
    }

    public void setMergeRequestsAndCommits(List<MergeRequest> mergeRequestsAndCommits) {
        this.mergeRequestsAndCommits = mergeRequestsAndCommits;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + devId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
