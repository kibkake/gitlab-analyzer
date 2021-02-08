package main.java.ConnectToGitlab.MergeRequests;//package main.java.ConnectToGitlab.MergeRequests;

import main.java.ConnectToGitlab.Developer.Developer;

import java.util.List;

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
    private int  author_id;
    private int author_username;
    private int approver_ids;
    private String created_at;
    private String created_before;
    private List<Developer> contributors;
    private String sha;

    public MergeRequest() {
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

    public void setMerged_at(String merged_at) {
        this.merged_at = merged_at;
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getAuthor_username() {
        return author_username;
    }

    public void setAuthor_username(int author_username) {
        this.author_username = author_username;
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
                ", author_id=" + author_id +
                ", author_username=" + author_username +
                ", approver_ids=" + approver_ids +
                ", created_at='" + created_at + '\'' +
                ", created_before='" + created_before + '\'' +
                ", contributors=" + contributors +
                ", sha='" + sha + '\'' +
                '}';
    }
}
