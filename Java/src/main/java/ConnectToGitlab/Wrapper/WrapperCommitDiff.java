package main.java.ConnectToGitlab.Wrapper;

/**
 * This class includes all of the important data about a commit diff, which represents
 * the changes made by a commit or merged merge request to a single file. Therefore,
 * a commit or merged merge request might have multiple diffs.
 */
public class WrapperCommitDiff {

    private String newPath;
    private String oldPath;
    private boolean newFile;
    private boolean renamedFile;
    private boolean deletedFile;
    private String diff;
    private double score;

    public WrapperCommitDiff() {
    }

    public WrapperCommitDiff(String newPath, String oldPath, boolean newFile, boolean renamedFile, boolean deletedFile, String diff) {
        this.newPath = newPath;
        this.oldPath = oldPath;
        this.newFile = newFile;
        this.renamedFile = renamedFile;
        this.deletedFile = deletedFile;
        this.diff = diff;
        score = calculateCommitScoreSingleDiff(diff);
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

    public double getScore() {
        return score;
    }
}
