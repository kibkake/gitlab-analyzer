package main.java.Collections;//package main.java.ConnectToGitlab.MergeRequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(value = "mergeRequest")
public class MergeRequest {

    @Id
    private Integer id;
    private int mergeRequestIdForASpecificProject; // this is the iid provided by gitLab
    private int projectId;
    private String title;
    private String mergedAt;
    private String updatedAt;
    private Developer author;
    private List<Developer> contributors;
    List<Commit> commits;
    private String sha;
    double mrScore;
    private Date mergedDate;
    private Date updatedDate;
    List<Note> allNotes;
    List<Note> codeReviewNotes;
    List<Diff> diffs;
    private String mrUrl;
    private double sumOfCommits;

    public double getSumOfCommits() {
        return sumOfCommits;
    }

    public void setSumOfCommits(double sumOfCommits) {
        this.sumOfCommits = sumOfCommits;
    }

    @JsonProperty("web_url")
    public String getMrUrl() {
        return mrUrl;
    }

    @JsonProperty("web_url")
    public void setMrUrl(String mrUrl) {
        this.mrUrl = mrUrl;
    }

    public MergeRequest() {
        contributors = new ArrayList<>();
    }

    public boolean isAContributor(String username) {
        for (Developer currentDev : contributors) {
            if (currentDev.getUsername().equals(username)) {
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

    @JsonProperty("project_id")
    public int getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("merged_at")
    public String getMergedAt() {
        return mergedAt;
    }

    // Spring calls this to create a mergeRequest object use it to create a Date
    @JsonProperty("merged_at")
    public void setMergedAt(String mergedAt) {
        this.mergedAt = mergedAt;
        if(mergedAt !=null) {
            OffsetDateTime dateWithOffSet = OffsetDateTime.parse(mergedAt);
            setMergedDate(Date.from(dateWithOffSet.toInstant()));
        }
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        OffsetDateTime dateWithOffSet = OffsetDateTime.parse(updatedAt);
        setUpdatedDate(Date.from(dateWithOffSet.toInstant()));
    }

    public Date getMergedDate() {
        return mergedDate;
    }

    public void setMergedDate(Date mergedDate) {
        this.mergedDate = mergedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Developer getAuthor() {
        return author;
    }

    public void setAuthor(Developer author) {
        this.author = author;
    }

    @JsonProperty("iid")
    public int getMergeRequestIdForASpecificProject() {
        return mergeRequestIdForASpecificProject;
    }

    @JsonProperty("iid")
    public void setMergeRequestIdForASpecificProject(int mergeRequestIdForASpecificProject) {
        this.mergeRequestIdForASpecificProject = mergeRequestIdForASpecificProject;
    }

    @Override
    public String toString() {
        return "MergeRequest{" +
                "id=" + id +
                ", project_id=" + projectId +
                ", title='" + title + '\'' +
                ", merged_at='" + mergedAt + '\'' +
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

    public void setMergeRequestNotes(List<Note> notes) {
        this.allNotes = notes;
        
        List<Note> tempCodeReviewNotes = new ArrayList<>();
        for (Note note : notes) {
            int noteAuthorId = note.getAuthor().getDevId();
            int MRContributorId = this.author.getDevId();
            if (noteAuthorId != MRContributorId) {
                tempCodeReviewNotes.add(note);
            }
        }
        this.codeReviewNotes = tempCodeReviewNotes;
    }

    public List<Note> getAllNotes() {
        return allNotes;
    }

    public List<Note> getCodeReviewNotes() {
        return codeReviewNotes;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MergeRequest other = (MergeRequest) o;
        return (mergeRequestIdForASpecificProject == other.mergeRequestIdForASpecificProject
                && projectId == other.projectId && Double.compare(other.mrScore, mrScore) == 0
                && Objects.equals(id, other.id) && Objects.equals(title, other.title)
                && Objects.equals(mergedAt, other.mergedAt)
                && Objects.equals(updatedAt, other.updatedAt) && Objects.equals(author, other.author)
                && Objects.equals(contributors, other.contributors)
                && Objects.equals(commits, other.commits) && Objects.equals(sha, other.sha)
                && Objects.equals(mergedDate, other.mergedDate)
                && Objects.equals(updatedDate, other.updatedDate)
                && Objects.equals(allNotes, other.allNotes)
                && Objects.equals(codeReviewNotes, other.codeReviewNotes)
                && Objects.equals(diffs, other.diffs) && Objects.equals(mrUrl, other.mrUrl));
    }
}
