package main.java.ConnectToGitlab.Commit;

public class CommitDiffs {
    private String new_path;
    private String old_path;
    private boolean new_file;
    private boolean renamed_file;
    private boolean deleted_file;
    private String diff;
    private double score;

    public CommitDiffs(String new_path, String old_path, boolean new_file, boolean renamed_file, boolean deleted_file, String diff, double score) {
        this.new_path = new_path;
        this.old_path = old_path;
        this.new_file = new_file;
        this.renamed_file = renamed_file;
        this.deleted_file = deleted_file;
        this.diff = diff;
        this.score = score;
    }

    public CommitDiffs() {
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

    public String getNew_path() {
        return new_path;
    }

    public void setNew_path(String new_path) {
        this.new_path = new_path;
    }

    public String getOld_path() {
        return old_path;
    }

    public void setOld_path(String old_path) {
        this.old_path = old_path;
    }

    public boolean isNew_file() {
        return new_file;
    }

    public void setNew_file(boolean new_file) {
        this.new_file = new_file;
    }

    public boolean isRenamed_file() {
        return renamed_file;
    }

    public void setRenamed_file(boolean renamed_file) {
        this.renamed_file = renamed_file;
    }

    public boolean isDeleted_file() {
        return deleted_file;
    }

    public void setDeleted_file(boolean deleted_file) {
        this.deleted_file = deleted_file;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
