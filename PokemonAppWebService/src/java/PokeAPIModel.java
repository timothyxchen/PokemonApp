

/*
* @author Joe Mertz
*
* This file is the Model component of the MVC, and it models the business
* logic for the web application.  In this case, the business logic involves
* making a request to flickr.com and then screen scraping the HTML that is
* returned in order to fabricate an image URL.
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;


class Result {
    String value;
    
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
public class PokeAPIModel {
    
    public String doPokeSearch(String searchTag)
            throws UnsupportedEncodingException  {
        Result r = new Result();
        int status = 0;
        if((status = doGet(searchTag,r)) != 200) return "Error from server "+ status;
        
        JSONObject pokeObj = new JSONObject(r.getValue());
        
        JSONObject combined = new JSONObject();
        combined.put("name", pokeObj.get("name"));

        JSONObject picObj = (JSONObject)pokeObj.get("sprites"); 
        String picUrl = (String) picObj.get("front_default");
        combined.put("picture", picUrl);
        System.out.println(combined.toString());
        String xml = XML.toString(combined,"pokeInfo");
        
        return xml;
    }
    
    
    public static int doGet(String name, Result r) {
        
        // Make an HTTP GET passing the name on the URL line
        
        r.setValue("");
        String response = "";
        HttpURLConnection conn;
        int status = 0;
        
        try {
            // pass the name on the URL line
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/"+name);
            System.out.println(url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setRequestMethod("GET");
            
            // wait for response
            status = conn.getResponseCode();
            
            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode();
            }
            String output = "";
            // things went well so let's read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            
            while ((output = br.readLine()) != null) {
                response += output;
                
            }
            
            conn.disconnect();
            
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }
        
        // return value from server
        // set the response object
        r.setValue(response);
        // return HTTP status to caller
        return status;
    }
}
