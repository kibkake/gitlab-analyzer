package main.java.Collections;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 * used for saving snapshots of gitlab repositories
 */
@Document(collection = "snapshot")
public class Snapshot {

    @Id
    private String id;
    private String username;
    private String startDate;
    private String endDate;
    private int projectId;
    private String dev;

    public Snapshot(){}

    public Snapshot(String username, String startDate, String endDate, int projectId, String dev) {
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.dev = dev;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    @Override
    public String toString() {
        return "Snapshot{" +
                "username=" + username +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectId=" + projectId +
                ", dev="+dev+
                '}';
    }
}
