package org.zanbel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlRequest {
    private final String AQL_URL = AqlClient.globalConfig.getProperty("artifactory.baseurl");
    private HttpRequestExecuter requestExecuter;
    private AqlParser aqlParser;

    public AqlRequest(String aqlRequestString){
        // aqlRequestString
        // This variable will determine the search stringBody for now will use the getAllItems
        requestExecuter = new HttpRequestExecuter(AQL_URL, HttpRequestExecuter.END_POINT.AQL, AqlRequestsStrings.getAllItems());
        aqlParser = new AqlParser();
    }

    public JSONArray getAqlObjArray(){
        String responseBody = requestExecuter.executeHttpRequest();
        JSONObject jsonObj = convertStringToJSON(responseBody);
        return  aqlParser.parseJsonToObj(jsonObj);
    }

    public List<AqlItem> getAqlItems(){
        String responseBody = requestExecuter.executeHttpRequest();
        JSONObject jsonObj = convertStringToJSON(responseBody);
        return  aqlParser.parseJson(jsonObj);
    }

    public JSONObject convertStringToJSON(String jsonString){
        try{
            return new JSONObject(jsonString.toString());
        }
        catch (JSONException je){
            System.out.println("Unable to parse response string to JSON object" + je.toString());
            return null;
        }
    }
}
