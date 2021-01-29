package User;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private String path;
    private String path_with_namespace;
    private LocalDate createdAt;
    private LocalDate lastActivityAt;
    private int openIssuesCount;
    private int commitCount; // get from statistic label in JSON
    private List<String> links; // URLS to: issues, merge_requests, repo_branches, labels, events and members

    public Project() {
    }

    public Project(int id) {
        this.id = id;
    }

    public Project(int id, String name, String path, String path_with_namespace, LocalDate createdAt,
                   LocalDate lastActivityAt, int openIssuesCount, int commitCount, List<String> links) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.path_with_namespace = path_with_namespace;
        this.createdAt = createdAt;
        this.lastActivityAt = lastActivityAt;
        this.openIssuesCount = openIssuesCount;
        this.commitCount = commitCount;
        this.links = links;
    }
}
