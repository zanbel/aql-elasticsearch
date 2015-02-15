package org.zanbel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by roy on 13-Feb-15.
 */
public class ElesticsearchConnector {
    private final String ELESTIC_URL = AqlClient.globalConfig.getProperty("elasticsearch.baseurl");
    private ObjectMapper mapper;

    public ElesticsearchConnector(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
    }

    // in case some manipulation on object is needed before indexing
    public void indexResultsFromAqlItems(List<AqlItem> aqlItems) {
        HttpRequestExecuter requestExecuter = new HttpRequestExecuter(ELESTIC_URL, HttpRequestExecuter.END_POINT.ELESTIC, null);
        try {
            StringEntity body;
            for (int i = 0; i < aqlItems.size(); i++) {
                body = new StringEntity(mapper.writeValueAsString(aqlItems.get(i)));
                requestExecuter.setBody(body);
                requestExecuter.executeHttpRequest();
            }
        }
        catch (Exception e){
            System.out.println("Unable to index item " + e.toString());
        }
    }

    public void indexResultsFromJsonArray(JSONArray objArray) {
        HttpRequestExecuter requestExecuter = new HttpRequestExecuter(ELESTIC_URL, HttpRequestExecuter.END_POINT.ELESTIC, null);
        try {
            for (int i = 0; i < objArray.length(); i++) {
                requestExecuter.setBody(new StringEntity(mapper.writeValueAsString(objArray.get(i))));
                requestExecuter.executeHttpRequest();
            }
        }
        catch (Exception e){
            System.out.println("Unable to index item " + e.toString());
        }
    }
}
