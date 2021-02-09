package Database.model.data;

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

    public void setCommitShortId(String commitShortId) {
        this.commitShortId = commitShortId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCommitStats(List<CommitStat> commitStats) {
        this.commitStats = commitStats;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
