package org.zanbel;

import org.apache.http.entity.StringEntity;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlRequestsStrings {

    public static StringEntity getAllItems(){
        try {
            return new StringEntity("items.find().include(\"name\",\"repo\", \"created\", \"updated\",\"stat.downloads\", \"type\",\"path\",\"size\",\"depth\")");
        }
        catch (Exception e){
            System.out.println("Unable to create body " + e.toString());
            return  null;
        }
    }

    public static StringEntity getBuildByNameAndNumber(String buildName, String buildNumber){
        try {
            return new StringEntity("items.find ( {\"$and\": [ {\"@build.name\": {\"$eq\":\"" + buildName + "\"} }, {\"@build.number\":{\"$eq\":" + buildNumber + "} } ] } )");
        }
        catch (Exception e){
            System.out.println("Unable to create body " + e.toString());
            return  null;
        }
    }
}