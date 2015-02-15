package org.zanbel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlParser {
    public List<AqlItem> aqlItems;
//    public List<org.zanbel.AqlRepo> aqlRepos;

    public AqlParser(){
        aqlItems = new ArrayList<AqlItem>();
//        aqlRepos = new ArrayList<org.zanbel.AqlRepo>();
    }

    public JSONArray parseJsonToObj(JSONObject json){
        try{
            return json.getJSONArray("results");
        }
        catch (JSONException je){
            System.out.println("Unable to parse JSON object to JSON array" + je.toString());
            return null;
        }
    }

    // Methos to aggregate item to use for additional data
    // count item per repo
    // count different path
    public List<AqlItem> parseJson(JSONObject json){
        try{
            JSONArray jsonArray = json.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                aqlItems.add(convertJsonEntryToAqlItem(jsonArray.getJSONObject(i)));
            }
            return aqlItems;
        }
        catch (JSONException je){
            System.out.println("Unable to parse JSON object to JSON array" + je.toString());
            return null;
        }
    }

    public AqlItem convertJsonEntryToAqlItem(JSONObject jsonObj){
        try {
            AqlItem aqlItem = new AqlItem(
                    jsonObj.getString("repo"),
                    jsonObj.getString("created"),
                    jsonObj.getString("updated"),
                    jsonObj.getString("name"),
                    jsonObj.getString("downloads"),
                    jsonObj.getString("type"),
                    jsonObj.getString("path"),
                    jsonObj.getString("size"));
            return aqlItem;
        }catch (JSONException je){
            System.out.println("Unable to parse Aql item to JSON entry " + je.toString());
            return null;
        }
    }
}
