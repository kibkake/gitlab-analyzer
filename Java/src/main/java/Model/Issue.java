package main.java.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Issue")
public class Issue {
    private int issueIdForServer;
    private int internalIssueId;
    private int projectId;
    private Developer author;
    private String title;
    private String createdAt;
    private String modifiedAt;
    private String username;
    private List<Note> notes;


    public Issue() {
    }

    @JsonProperty("id")
    public int getIssueIdForServer() {
        return issueIdForServer;
    }

    public void setId(int issueIdForServer) {
        this.issueIdForServer = issueIdForServer;
    }

    @JsonProperty("project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Developer getAuthor() {
        return author;
    }

    public void setAuthor(Developer author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @JsonProperty("modified_at")
    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @JsonProperty("iid")
    public int getIssueIdForASpecificProject() {
        return internalIssueId;
    }

    @JsonProperty("iid")
    public void setInternalIssueId(int internalIssueId) {
        this.internalIssueId = internalIssueId;
    }
}
