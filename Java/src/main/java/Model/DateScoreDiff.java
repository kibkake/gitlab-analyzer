package main.java.Model;

public class DateScoreDiff{

    
    private String new_path;
    private double diffScore;

    public DateScoreDiff(Diff diff) {
        this.new_path = diff.getNew_path();
        this.diffScore = diff.getDiffScore();
    }


    public DateScoreDiff(){
    }

    public String getNew_path(){
        return this.new_path;
    }

    public double getDiffScore(){
        return this.diffScore;
    }





}