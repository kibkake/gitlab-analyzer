package DatabaseClasses.model.data;

import java.util.List;

public class Commits {

    private String commitShortId;
    private String authorName;
    private String title;
    private List<CommitStat> commitStats;
    private int created_at;

    public Commits(String commitShortId, String authorName, String title,
                   List<CommitStat> commitStats, int created_at) {
        this.commitShortId = commitShortId;
        this.authorName = authorName;
        this.title = title;
        this.commitStats = commitStats;
        this.created_at = created_at;
    }
}
