package main.java.Collections;

import java.util.Objects;

public class DateScoreDiff{

    
    private String new_path;
    private double diffScore;

    public DateScoreDiff(Diff diff) {
        this.new_path = diff.getNew_path();
        this.diffScore = diff.getDiffScore();
    }

    public DateScoreDiff(String newPath, double diffScore) {
        this.new_path = newPath;
        this.diffScore = diffScore;
    }

    public DateScoreDiff(){
    }

    public String getNew_path(){
        return this.new_path;
    }

    public double getDiffScore(){
        return this.diffScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateScoreDiff that = (DateScoreDiff) o;
        return Double.compare(that.diffScore, diffScore) == 0 && Objects.equals(new_path, that.new_path);
    }
}