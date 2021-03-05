package main.java.Model;//package main.java.ConnectToGitlab.MergeRequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(value = "mergeRequest")
public class MergeRequest {

    @Id
    private Integer id;
    private int iid;
    private int projectId;
    private String title;
    private String description;
    private String state;
    private String mergedAt;
    private int authorId;
    private String authorUsername;
    private List<Developer> contributors;
    List<Commit> commits;
    private String sha;
    double mrScore;
    private Date mergedDate;
    List<Note> notes;
    List<Diff> diffs;

    public MergeRequest() {
        contributors = new ArrayList<>();
    }

    public boolean isAContributor(String name) {
        for (Developer currentDev: contributors) {
            if (currentDev.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    @JsonProperty("project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("merged_at")
    public String getMergedAt() {
        return mergedAt;
    }

    // Spring calls this to create a mergeRequest object use it to create a Date
    public void setMergedAt(String mergedAt) {
        this.mergedAt = mergedAt;
        if(mergedAt !=null) {
            OffsetDateTime dateWithOffSet = OffsetDateTime.parse(mergedAt);
            setMergedDate(Date.from(dateWithOffSet.toInstant()));
        }
    }

    @JsonProperty("author_id")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @JsonProperty("author_username")
    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public List<Developer> getContributors() {
        return contributors;
    }

    public void setContributors(List<Developer> contributors) {
        this.contributors = contributors;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Date getMergedDate() {
        return mergedDate;
    }

    public void setMergedDate(Date mergedDate) {
        this.mergedDate = mergedDate;
    }

    @Override
    public String toString() {
        return "MergeRequest{" +
                "id=" + id +
                ", iid=" + iid +
                ", project_id=" + projectId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", merged_at='" + mergedAt + '\'' +
                ", author_id=" + authorId +
                ", author_username=" + authorUsername +
                ", contributors=" + contributors +
                ", sha='" + sha + '\'' +
                '}';
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public double getMrScore() {
        return mrScore;
    }

    public void setMrScore(double mrScore) {
        this.mrScore = mrScore;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes9) {
        this.notes = notes;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }
}
