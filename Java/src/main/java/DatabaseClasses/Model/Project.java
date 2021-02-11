package main.java.DatabaseClasses.Model;

import main.java.ConnectToGitlab.Commit.Commit;
import main.java.ConnectToGitlab.Developer.Developer;
import main.java.ConnectToGitlab.Issue.Issue;
import main.java.ConnectToGitlab.MergeRequests.MergeRequest;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Holds the different info for a project when getting a list of projects and is used for turning JSON to object with
 * spring
 */

import java.util.List;

@Document("Project")
public class Project {
    private int id;
    private String description;
    private String name;
    private String created_at;
    private String default_branch;
    private String readme_url;
    private String last_activity_at;
    private List<MergeRequest> mergedRequests;
    private List<Issue> issues;
    private List<Commit> commits;
    private List<Developer> developers;



    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public String getReadme_url() {
        return readme_url;
    }

    public void setReadme_url(String readme_url) {
        this.readme_url = readme_url;
    }

    public String getLast_activity_at() {
        return last_activity_at;
    }

    public void setLast_activity_at(String last_activity_at) {
        this.last_activity_at = last_activity_at;
    }

    public List<MergeRequest> getMergedRequests() {
        return mergedRequests;
    }

    public void setMergedRequests(List<MergeRequest> mergedRequests) {
        this.mergedRequests = mergedRequests;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
