package main.java.Model;//package main.java.ConnectToGitlab.MergeRequests;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(value = "MergeRequest")
public class MergeRequest {

    private int id;
    private int iid;
    private int project_id;
    private String title;
    private String description;
    private String state;
    private String merged_at;
    private String target_branch;
    private String updated_after;
    private String updated_before;
    private Developer author;
    private int approver_ids;
    private String created_at;
    private String created_before;
    private List<Developer> contributors;
    List<Commit> commits;
    private String sha;
    double mrScore;
    private Date mergedDate;
    List<Note> allNotes;
    List<Note> codeReviewNotes;
    List<Diff> diffs;

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

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
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

    public String getMerged_at() {
        return merged_at;
    }

    // Spring calls this to create a mergeRequest object use it to create a Date
    public void setMerged_at(String merged_at) {
        this.merged_at = merged_at;
        if(merged_at !=null) {
            System.out.println(merged_at);
            OffsetDateTime dateWithOffSet = OffsetDateTime.parse(merged_at);
            setMergedDate(Date.from(dateWithOffSet.toInstant()));
        }
    }

    public String getTarget_branch() {
        return target_branch;
    }

    public void setTarget_branch(String target_branch) {
        this.target_branch = target_branch;
    }

    public String getUpdated_after() {
        return updated_after;
    }

    public void setUpdated_after(String updated_after) {
        this.updated_after = updated_after;
    }

    public String getUpdated_before() {
        return updated_before;
    }

    public void setUpdated_before(String updated_before) {
        this.updated_before = updated_before;
    }

    public int getApprover_ids() {
        return approver_ids;
    }

    public void setApprover_ids(int approver_ids) {
        this.approver_ids = approver_ids;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_before() {
        return created_before;
    }

    public void setCreated_before(String created_before) {
        this.created_before = created_before;
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

    public Developer getAuthor() {
        return author;
    }

    public void setAuthor(Developer author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "MergeRequest{" +
                "id=" + id +
                ", iid=" + iid +
                ", project_id=" + project_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", merged_at='" + merged_at + '\'' +
                ", target_branch='" + target_branch + '\'' +
                ", updated_after='" + updated_after + '\'' +
                ", updated_before='" + updated_before + '\'' +
                ", approver_ids=" + approver_ids +
                ", created_at='" + created_at + '\'' +
                ", created_before='" + created_before + '\'' +
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

    public void setNotes(List<Note> notes) {
        this.allNotes = notes;
        
        List<Note> tempCodeReviewNotes = new ArrayList<>();
        for (Note note : notes) {
            int noteAuthorId = note.getAuthor().getId();
            int MRContributorId = contributors.get(0).getId();
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
}
