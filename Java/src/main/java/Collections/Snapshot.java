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
    private String name;
    private String startDate;
    private String endDate;
    private int projectId;

    public Snapshot(){}

    public Snapshot(String name, String startDate, String endDate, int projectId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Snapshot{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectId=" + projectId +
                '}';
    }
}
