package sparql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RunQuery {
	private String endPoint = "http://rdf.myexperiment.org/sparql/?query=";
	
public String runQuery(String paramQuery){
        
    	String requestURL=null;

			try {
				requestURL = endPoint + URLEncoder.encode(paramQuery, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		    String response = "";
		    BufferedReader rd = null;
		    try {
		        URL url = new URL(requestURL);
		        URLConnection conn2 = url.openConnection();
		        conn2.setRequestProperty("Accept", "application/rdf+xml");
		        rd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
		        
		        String line;
		        while ((line = rd.readLine()) != null) {
		            //Process line...
		            response= response+line+"\n";
		        }
		    } catch (Exception e) {
		        System.out.println("Web request failed");
		    // Web request failed
		    } finally {
		        if (rd != null) {
		            try {
		                rd.close();
		            } catch (IOException ex) {
		                System.out.println("Problema al cerrar el objeto lector");
		            }
		        }
		    }

			return response;
       
	}

}
