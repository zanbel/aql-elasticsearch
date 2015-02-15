package org.zanbel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlItem {
    public String repo;
    public String created;
    public String updated;
    public String name;
    public int downloads;
    public String type;
    public String path;
    public int size;
    public Date date;
    public String sDate;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public AqlItem(String repo, String created, String updated, String name, String downloads, String type, String path, String size){
        this.repo = repo;
        this.name = name;
        this.downloads = Integer.parseInt(downloads);
        this.created = created;
        this.updated = updated;
        this.type = type;
        this.path = path;
        this.date = new Date();
        this.sDate = dateFormat.format(date);
        this.size = Integer.parseInt(size);
    }

//    public String toJsonString(){
//        return "{ \"_timestamp\":\"" + date + "\"," +
//                "\"name\":\"" + name + "\"," +
//                "\"created\":\"" + created + "\"," +
//                "\"updated\":\"" + updated + "\"," +
//                "\"repo\":\"" + repo + "\"," +
//                "\"downloads\":" + downloads + "," +
//                "\"type\":\"" + type + "\"," +
//                "\"size\":" + size + "," +
//                "\"path\":\"" + path + "\"}";
//    }
}
