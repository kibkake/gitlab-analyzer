package main.java.ConnectToGitlab.Wrapper;

public class WrapperCommitDiff {

    String newPath;
    String oldPath;
    boolean newFile;
    boolean renamedFile;
    boolean deletedFile;
    String diff;

    public WrapperCommitDiff(String newPath, String oldPath, boolean newFile, boolean renamedFile, boolean deletedFile, String diff) {
        this.newPath = newPath;
        this.oldPath = oldPath;
        this.newFile = newFile;
        this.renamedFile = renamedFile;
        this.deletedFile = deletedFile;
        this.diff = diff;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getOldPath() {
        return oldPath;
    }

    public boolean isNewFile() {
        return newFile;
    }

    public boolean isRenamedFile() {
        return renamedFile;
    }

    public boolean isDeletedFile() {
        return deletedFile;
    }

    public String getDiff() {
        return diff;
    }
}
