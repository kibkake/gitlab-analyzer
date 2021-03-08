package main.java.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "userQuery")
public class ProjectSettings {

    private String queryName;
    private LocalDate queryStartDate;
    private LocalDate queryEndDate;
    private int projectId;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public LocalDate getQueryStartDate() {
        return queryStartDate;
    }

    public void setQueryStartDate(LocalDate queryStartDate) {
        this.queryStartDate = queryStartDate;
    }

    public LocalDate getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(LocalDate queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
