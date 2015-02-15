package org.zanbel;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by roy on 13-Feb-15.
 */
public class HttpRequestExecuter {

    private HttpPost request;
    private END_POINT endPoint;

    public HttpRequestExecuter(String url, END_POINT endPoint, StringEntity body){
        this.endPoint = endPoint;
        request = new HttpPost(url);
        request.setEntity(body);
        prepareRequest();
    }

    public void setBody(StringEntity body){
        request.setEntity(body);
    }

    public String executeHttpRequest(){
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() + " " + response.getEntity().toString());
            }
            return digestResponse(response);
        }
        catch (Exception e){
            System.out.println("Unable to send POST request " + e.toString());
            return null;
        }
    }

    private String digestResponse(HttpResponse response){
        return endPoint == END_POINT.ELESTIC ? digestElesticResponse(response) : digestAQLResponse(response);
    }

    private String digestElesticResponse(HttpResponse response){
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Printing Elestic response");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            return "";
        }
        catch(IOException e){
            System.out.println("Unable to read Elesticsearch response " + e.toString());
            return null;
        }
    }

    // might be an issue for large DBs
    // need to limit the number of results and use pagination
    private String digestAQLResponse(HttpResponse response){
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            StringBuilder jsonString = new StringBuilder();

            String output;
            while ((output = br.readLine()) != null) {
                // System.out.println("Printing AQL response");
                // System.out.println(output);
                jsonString.append(output);
            }
            return jsonString.toString();
        }
        catch(IOException e){
            System.out.println("Unable to read AQL response " + e.toString());
            return null;
        }
        catch(Exception e){
            System.out.println("Unable to read AQL response " + e.toString());
            return null;
        }
    }

    private void prepareRequest(){
        if(endPoint == END_POINT.AQL) {
            request.setHeader("Consumes", "text/plain");
            setAuthorizationHeader();
        }
        if(endPoint == END_POINT.ELESTIC) {
            //request.setHeader("Content-type", "application/json");
            request.addHeader("content-type", "application/x-www-form-urlencoded");
        }
    }

    private static String getCred(String user, String pass) {
        String authCred = user + ":" + pass;
        String autString = Base64.encodeBase64String(authCred.getBytes());
        return autString;
    }

    private void setAuthorizationHeader(){
        request.setHeader("Authorization", "Basic " +
                getCred(AqlClient.globalConfig.getProperty("artifactory.username"),
                AqlClient.globalConfig.getProperty("artifactory.password")));
    }

    public enum END_POINT {
        AQL,
        ELESTIC
    }
}
