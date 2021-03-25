package main.java.Collections;

import java.util.Objects;

public class Diff {
    private String new_path;
    private String old_path;
    private boolean new_file;
    private boolean renamed_file;
    private boolean deleted_file;
    private String diff;
    private double diffScore;

    public Diff(String new_path, String old_path, boolean new_file, boolean renamed_file, boolean deleted_file, String diff, double diffScore) {
        this.new_path = new_path;
        this.old_path = old_path;
        this.new_file = new_file;
        this.renamed_file = renamed_file;
        this.deleted_file = deleted_file;
        this.diff = diff;
        this.diffScore = diffScore;
    }

    public Diff() {
    }

    /**
     * Calculates the score from a diff string
     * the actual string containing the changes made to the file
     */
    public void calculateAndSetDiffScore() {
        double score = 0.0;
        for(int j = 0; j < this.diff.length(); j++){
            if(this.diff.charAt(j) == '\n' && j < this.diff.length()-2){
                j++;
                if(this.diff.charAt(j) == '+'){
                    j++;
                    while(this.diff.charAt(j) == ' ' || this.diff.charAt(j) == '\t'){
                        j++;
                    }
                    if(this.diff.charAt(j) == '\n'){
                        j--;
                    }else if(this.diff.charAt(j) == '/'){
                        score += 0.0;

                    }else if(this.diff.charAt(j) == '}' || this.diff.charAt(j) == '{'){
                        score += 0.2;
                    }else{
                        score += 1.0;
                    }
                }
                if(this.diff.charAt(j) == '-'){
                    score += 0.2;
                }
            }
        }
        score = Math.round(score * 100.0) / 100.0;
        this.diffScore = score;
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

    public double getDiffScore() {
        return diffScore;
    }

    public void setDiffScore(double diffScore) {
        this.diffScore = diffScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diff diff1 = (Diff) o;
        return (new_file == diff1.new_file && renamed_file == diff1.renamed_file
                && deleted_file == diff1.deleted_file && Double.compare(diff1.diffScore, diffScore) == 0
                && Objects.equals(new_path, diff1.new_path) && Objects.equals(old_path, diff1.old_path)
                && Objects.equals(diff, diff1.diff));
    }
}
