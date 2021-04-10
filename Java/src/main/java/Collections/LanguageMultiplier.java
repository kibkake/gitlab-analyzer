package main.java.Collections;

public class LanguageMultiplier {

    private String name;
    private String extension;
    private double multiplier;

    public LanguageMultiplier(){

    }

    public LanguageMultiplier(String name, String extension, double multiplier){
        this.name=name;
        this.extension=extension;
        this.multiplier=multiplier;
    }

    public String getName(){
        return this.name;
    }

    public String getExtension(){
        return this.extension;
    }

    public double getMultiplier(){
        return this.multiplier;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setExtension(String extension){
        this.extension=extension;
    }

    public void setMultiplier(double multiplier){
        this.multiplier=multiplier;
    }
    
}
