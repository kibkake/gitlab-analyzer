package main.java.Collections;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MergeRequestDiff other = (MergeRequestDiff) o;
        return Objects.equals(changes, other.changes);
    }
}
