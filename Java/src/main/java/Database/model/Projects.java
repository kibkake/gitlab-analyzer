package Database.model;

import DatabaseClasses.model.data.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document (collection = "Projects")
public class Projects {

    @Id
    private int projectId;
    private String projectName;
    private String created_at;
    private List<Member> memberList;
    private List<Commits> commitsList;
    private List<MergedRequest> mergedRequestList;
    private List<Issue> IssueList;

    public Projects (int projectId, String projectName, String created_at) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.created_at = created_at;
    }

    public Projects (int projectId, String projectName, String created_at, List<Member> memberList) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.created_at = created_at;
        this.memberList = memberList;
    }

    public Projects (int projectId, String projectName, String created_at, List<Member> memberList,
                     List<Commits> commitsList, List<MergedRequest> mergedRequestList, List<Issue> issueList) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.created_at = created_at;
        this.memberList = memberList;
        this.commitsList = commitsList;
        this.mergedRequestList = mergedRequestList;
        IssueList = issueList;
    }
}
