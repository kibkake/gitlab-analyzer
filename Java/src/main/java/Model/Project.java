package main.java.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Holds the different info for a project when getting a list of projects and is used for turning JSON to object with
 * spring
 */

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document("Project")
public class Project {
    private int id;
    private String description;
    private String name;
    private String createdAt;
    private String lastActivityAt;
    private List<MergeRequest> mergedRequests;
    private List<Issue> issues;
    private List<Commit> commits;
    private List<Developer> developers;
    private boolean infoSet;
    private Instant infoSetDate;


    public Project() {
        mergedRequests = new ArrayList<>();
        issues = new ArrayList<>();
        commits = new ArrayList<>();
        developers = new ArrayList<>();
        // The point of initializing them to empty arraylists is that, if they're not ever
        // given values, they will be empty lists instead of equaling null.
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

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    //Note this is different from the data from the GitLab API field
    @JsonProperty("last_activity_at")
    public String getLastActivityAt() {
        return lastActivityAt;
    }

    @JsonProperty("last_activity_at")
    public void setLastActivityAt(String lastActivityAt) {

        this.lastActivityAt = lastActivityAt;
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
//        this.lastActivityAt = formatter.format(this.infoSetDate);
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

    public boolean isInfoSet() {
        return infoSet;
    }

    public void setSyncInfo() {
        this.infoSet = true;
        this.infoSetDate = Clock.systemUTC().instant();
    }

    public boolean projectHasBeenUpdated() {
        Instant lastUpdateDate = lastProjectUpdateDate();
        return lastUpdateDate.compareTo(infoSetDate) > 0;
    }

    private Instant lastProjectUpdateDate() {
        Instant mostRecentMergeRequestUpdateDate = new main.java.ConnectToGitlab.MergeRequestConnection().getMostRecentMergeRequestUpdateDate(id);
        Instant mostRecentIssueUpdateDate = new main.java.ConnectToGitlab.IssueConnection().getMostRecentIssueUpdateDate(id);
        Instant mostRecentCommitDate = new main.java.ConnectToGitlab.CommitConnection().getMostRecentCommitDate(id);

        Instant mostRecentUpdateDate = mostRecentMergeRequestUpdateDate;

        if (mostRecentIssueUpdateDate.compareTo(mostRecentUpdateDate) > 0) {
            mostRecentUpdateDate = mostRecentIssueUpdateDate;
        }
        if (mostRecentCommitDate.compareTo(mostRecentUpdateDate) > 0) {
            mostRecentUpdateDate = mostRecentCommitDate;
        }
        return mostRecentUpdateDate;
    }


    public Instant getInfoSetDate() {
        return infoSetDate;
    }

    public void setInfoSetDate(Instant infoSetDate) {
        this.infoSetDate = infoSetDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", mergedRequests=" + mergedRequests +
                ", issues=" + issues +
                ", commits=" + commits +
                ", developers=" + developers +
                ", infoSet=" + infoSet +
                ", infoSetDate=" + infoSetDate +
                '}';
    }
}
