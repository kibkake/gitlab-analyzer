package main.java.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "userQuery")
public class UserQuery {

    private String queryName;
    private Date queryStartDate;
    private Date queryEndDate;
    private Date projectId;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Date getQueryStartDate() {
        return queryStartDate;
    }

    public void setQueryStartDate(Date queryStartDate) {
        this.queryStartDate = queryStartDate;
    }

    public Date getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(Date queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public Date getProjectId() {
        return projectId;
    }

    public void setProjectId(Date projectId) {
        this.projectId = projectId;
    }
}
