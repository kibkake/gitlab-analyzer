package main.java.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.objectweb.asm.TypeReference;

import java.util.*;

public class Configuration {

    private String id;
    private String name;
    private List<LanguageMultiplier> Multipliers;
    
    public Configuration(String newMultipliers) throws JSONException {
        JSONArray jsonMultipliers = new JSONArray(newMultipliers);
        for(int k=0;k<jsonMultipliers.length();k++){
            JSONObject obj = jsonMultipliers.getJSONObject(k);
            String objName = "";
            String objExtension="";
            double objMultiplier=1;
            try {
                objName = obj.getString("name");
                objExtension = obj.getString("extension");
                objMultiplier = obj.getDouble("multiplier");
            }catch (JSONException e) {
                e.printStackTrace();
            }
            LanguageMultiplier multiplier = new LanguageMultiplier(objName,objExtension,objMultiplier);
            this.Multipliers.add(multiplier);
        }
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setMultipliers(List<LanguageMultiplier> newMultipliers){
        this.Multipliers=newMultipliers;
    }
    public List<LanguageMultiplier> getMultipliers(){
        return this.Multipliers;
    }


}
