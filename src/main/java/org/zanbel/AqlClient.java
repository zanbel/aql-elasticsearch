package org.zanbel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by roy on 13-Feb-15.
 */
public class AqlClient {
    private AqlRequest request;
    private ElesticsearchConnector elesConnector;
    public static Properties globalConfig;

    public AqlClient(String aqlRequestString){
        globalConfig = loadConfig(System.getProperty("user.dir") + "\\config\\properties");
        if(globalConfig == null) {
            System.out.println("Unable to run load config file, exiting");
            System.exit(1);
        }
        request = new AqlRequest(aqlRequestString);
        elesConnector = new ElesticsearchConnector();
    }

    /***
     * @param args #1 AQLRequestString
     * TODO:
     * 1 - option to send credentials
     * 2 - option to send endpoint
     * 3 - option to send query and include fields
     * 4 - send path to config
     */
    public static void main(String [ ] args)
    {
        String aqlRequestString = (args.length == 0) ? "getAllItems" : args[0];
        AqlClient client = new AqlClient(aqlRequestString);
        try {
            client.run();
        }
        catch (Exception e){
            System.out.println("Unable to run client " + e.toString());
        }
    }

    public void run() {
/**this section should be uncommented in case you wish to add manipulations, or extract additional fields before indexing
 * TODO:
 * add manipulation to create repo items in AQL index and map fields, e.g. total size, num of artifact, type, path, etc...
 *
 *        List<org.zanbel.AqlItem> aqlItems = request.getAqlItems();
 *        elesConnector.indexResultsFromAqlItems(aqlItems);
**/

        elesConnector.indexResultsFromJsonArray(request.getAqlObjArray());
    }

    private Properties loadConfig(String pathToFile){
        Properties configProps = new Properties();

        try {
            InputStream inStream = new FileInputStream(pathToFile);
            configProps.load(inStream);
            if (inStream != null)
                inStream.close();
            return configProps;
        }
        catch(Exception e){
            System.out.println("Unable to load properties");
            e.printStackTrace();
            return null;
        }
    }
}