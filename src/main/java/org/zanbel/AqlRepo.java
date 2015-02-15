package org.zanbel;

import java.util.Date;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlRepo {
    private int numOfArtifacts;
    private int sizeInMB;
    private Date lastUpdated;

    public AqlRepo(){
        lastUpdated = new Date();
    }

    public void addArtifact(){
        this.numOfArtifacts++;
    }

    public void setSizeInMB(int sizeInMB) {
        this.sizeInMB += sizeInMB;
    }

    public void updateLastUpdate(Date newDate){
        this.lastUpdated = newDate;
    }
}
