package DatabaseClasses.model;

import DatabaseClasses.model.data.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Document (collection = "Projects")
public class Projects {

    @Id
    private int PROJECT_ID;
    private String PROJECT_NAME;
    private List<MergedRequest> MERGED_MERGE_REQUESTS;
    private List<Issue> ALL_ISSUES;

    public Projects() {

    }

    public Projects(int PROJECT_ID, String PROJECT_NAME) {
        this.PROJECT_ID = PROJECT_ID;
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public Projects(int PROJECT_ID, String PROJECT_NAME,
                    List<MergedRequest> MERGED_MERGE_REQUESTS, List<Issue> ALL_ISSUES) {
        this.PROJECT_ID = PROJECT_ID;
        this.PROJECT_NAME = PROJECT_NAME;
        this.MERGED_MERGE_REQUESTS = MERGED_MERGE_REQUESTS;
        this.ALL_ISSUES = ALL_ISSUES;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPROJECT_NAME() {
        return PROJECT_NAME;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    @Override
    public String toString() {
        return "Projects{" +
                "PROJECT_ID=" + PROJECT_ID +
                ", PROJECT_NAME='" + PROJECT_NAME + '\'' +
                '}';
    }
}
