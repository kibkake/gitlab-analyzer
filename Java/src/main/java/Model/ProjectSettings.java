package main.java.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "userQuery")
public class ProjectSettings {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ProjectSettings{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectId=" + projectId +
                '}';
    }
}
