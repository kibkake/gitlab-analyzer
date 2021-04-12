package main.java.Collections;

import org.json.JSONException;

public class Configuration {

    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String languageScale;
    
    public Configuration(String name, String startDate, String endDate,String languageScale) throws JSONException {
        this.name=name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.languageScale =languageScale;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public  String getName(){return this.name;}

    public void setName(String name){ this.name=name;}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setLanguageScale(String newMultipliers){
        this.languageScale =newMultipliers;
    }

    public String getLanguageScale(){
        return this.languageScale;
    }

    @Override
    public String toString(){
        return "Config{"+
                "name="+name+
                "startdate="+startDate+
                "endDate="+endDate+
                "languageScale="+languageScale+
                "}";
    }
}
