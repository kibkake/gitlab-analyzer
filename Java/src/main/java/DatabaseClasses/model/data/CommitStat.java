package DatabaseClasses.model.data;

public class CommitStat {
    private int additions;
    private int deletions;
    private int total;

    public CommitStat(int additions, int deletions, int total) {
        this.additions = additions;
        this.deletions = deletions;
        this.total = total;
    }
}
