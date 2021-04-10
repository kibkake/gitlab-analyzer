package main.java.Collections;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * used for saving snapshots of gitlab repositories
 */
@Document(collection = "snapshot")
public class Snapshot {

    @Id
    private String id;
    private String username;
    private LocalDate startDate;
    private LocalDate endDate;
    private int projectId;
    private String dev;
    private String page;

    public Snapshot(){}

    public Snapshot(String id, String username, LocalDate startDate, LocalDate endDate, int projectId, String dev, String page) {
        this.id = id;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.dev = dev;
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDev(){return dev;}

    public void setDev(String dev){this.dev = dev;  }

    public String getPage(){return page;}

    public void setPage(String page){this.page = page;  }

    @Override
    public String toString() {
        return "Snapshot{" +
                "username=" + username +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectId=" + projectId +
                ", dev="+dev+
                ", page="+page+
                '}';
    }
}
