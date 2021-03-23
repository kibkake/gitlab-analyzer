package main.java.Collections;

import java.util.List;

/**
 * Spring needs an object to map names to a merge request diff has a lot of repeat information so we just want the
 * changes this is a list of diffs.
 */
public class MergeRequestDiff {
    List<Diff> changes;

    public MergeRequestDiff() {
    }

    public List<Diff> getChanges() {
        return changes;
    }

    public void setChanges(List<Diff> changes) {
        this.changes = changes;
    }
}
