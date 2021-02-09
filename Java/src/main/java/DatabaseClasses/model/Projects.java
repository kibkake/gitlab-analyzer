package main.java.DatabaseClasses.model;

import main.java.DatabaseClasses.model.data.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document (collection = "Projects")
public class Projects {

    @Id
    private int projectId;
    private String projectName;
    private List<MergedRequest> MERGED_MERGE_REQUESTS;
    private List<Issue> ALL_ISSUES;

    public Projects() {

    }

    public Projects(int PROJECT_ID, String PROJECT_NAME) {
        this.projectId = PROJECT_ID;
        this.projectName = PROJECT_NAME;
    }

    public Projects(int PROJECT_ID, String PROJECT_NAME,
                    List<MergedRequest> MERGED_MERGE_REQUESTS, List<Issue> ALL_ISSUES) {
        this.projectId = PROJECT_ID;
        this.projectName = PROJECT_NAME;
        this.MERGED_MERGE_REQUESTS = MERGED_MERGE_REQUESTS;
        this.ALL_ISSUES = ALL_ISSUES;
    }

    public int getPROJECT_ID() {
        return projectId;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.projectId = PROJECT_ID;
    }

    public String getPROJECT_NAME() {
        return projectName;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.projectName = PROJECT_NAME;
    }

    @Override
    public String toString() {
        return "Projects{" +
                "PROJECT_ID=" + projectId +
                ", PROJECT_NAME='" + projectName + '\'' +
                '}';
    }
}
