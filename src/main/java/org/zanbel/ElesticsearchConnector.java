package org.zanbel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by roy on 13-Feb-15.
 */
public class ElesticsearchConnector {
    private final String ELESTIC_URL = AqlClient.globalConfig.getProperty("elasticsearch.baseurl");
    private ObjectMapper mapper;
    private Date date;
    private final String FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat df;
    private String sDate;

    public ElesticsearchConnector(){
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JsonOrgModule());
        this.df = new SimpleDateFormat(FORMAT);
        this.date = new Date();
        this.sDate = df.format(date);
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
        JSONObject o;
        try {
            for (int i = 0; i < objArray.length(); i++) {
                o = (JSONObject)objArray.get(i);
                o.put("post-date", sDate);
                requestExecuter.setBody(new StringEntity(mapper.writeValueAsString(objArray.get(i))));
                requestExecuter.executeHttpRequest();
            }
        }
        catch (Exception e){
            System.out.println("Unable to index item " + e.toString());
        }
    }

//    public void indexResultsFromJsonArrayWithElasticClient(JSONArray objArray) {
//
//        try {
//
//            IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
//                    .setSource(jsonBuilder()
//                                    .startObject()
//                                    .field("user", "kimchy")
//                                    .field("postDate", new Date())
//                                    .field("message", "trying out Elasticsearch")
//                                    .endObject()
//                    )
//                    .execute()
//                    .actionGet();
//        }
//        catch (Exception e){
//            System.out.println("Unable to index item " + e.toString());
//        }
//    }
}
