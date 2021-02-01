package main.java.Commit;

/**
 * Used by commits to hold the number of modification a commit has
 */
public class Stats {
    private int additions;
    private int deletions;
    private int total;

    public Stats() {
    }

    public Stats(int additions, int deletions, int total) {
        this.additions = additions;
        this.deletions = deletions;
        this.total = total;
    }

    public int getAdditions() {
        return additions;
    }

    public void setAdditions(int additions) {
        this.additions = additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public void setDeletions(int deletions) {
        this.deletions = deletions;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
