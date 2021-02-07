package DatabaseClasses.model;

import DatabaseClasses.model.data.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document (collection = "Projects")
public class Projects {

    @Id
    private int id;
    private String name;
    private String created_at;
    private List<Member> memberList;
    private List<Commits> commitsList;
    private List<MergedRequest> mergedRequestList;
    private List<Issue> IssueList;

}
