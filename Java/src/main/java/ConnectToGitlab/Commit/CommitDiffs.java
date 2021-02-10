package main.java.ConnectToGitlab.Commit;

public class CommitDiffs {
    private final String NEW_PATH;
    private final String OLD_PATH;
    private final boolean NEW_FILE;
    private final boolean RENAMED_FILE;
    private final boolean DELETED_FILE;
    private final String DIFF;
    private final double SCORE;

    public WrapperCommitDiff(String newPath, String oldPath, boolean newFile, boolean renamedFile, boolean deletedFile, String diff) {
        this.NEW_PATH = newPath;
        this.OLD_PATH = oldPath;
        this.NEW_FILE = newFile;
        this.RENAMED_FILE = renamedFile;
        this.DELETED_FILE = deletedFile;
        this.DIFF = diff;
        SCORE = calculateCommitScoreSingleDiff(diff);
    }

    /**
     * Calculates the score from a diff string
     * @param diff the actual string containing the changes made to the file
     */
    private double calculateCommitScoreSingleDiff(String diff) {
        double score = 0.0;
        for(int j = 0; j < diff.length(); j++){
            if(diff.charAt(j) == '\n' && j < diff.length()-2){
                j++;
                if(diff.charAt(j) == '+'){
                    j++;
                    while(diff.charAt(j) == ' ' || diff.charAt(j) == '\t'){
                        j++;
                    }
                    if(diff.charAt(j) == '\n'){
                        j--;
                    }else if(diff.charAt(j) == '/'){
                        score += 0.0;

                    }else if(diff.charAt(j) == '}' || diff.charAt(j) == '{'){
                        score += 0.2;
                    }else{
                        score += 1.0;
                    }
                }
                if(diff.charAt(j) == '-'){
                    score += 0.2;
                }
            }
        }
        return Math.round(score * 100.0) / 100.0;
    }

    public String getNewPath() {
        return NEW_PATH;
    }

    public String getOldPath() {
        return OLD_PATH;
    }

    public boolean isNewFile() {
        return NEW_FILE;
    }

    public boolean isRenamedFile() {
        return RENAMED_FILE;
    }

    public boolean isDeletedFile() {
        return DELETED_FILE;
    }

    public String getDiff() {
        return DIFF;
    }

    public double getScore() {
        return SCORE;
    }
}
