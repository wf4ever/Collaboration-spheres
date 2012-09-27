package sparql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RunRecommender {
	private String endPoint = "http://calatola.man.poznan.pl/epnoiServer/rest/recommender/";
	
public String runQueryHistoric(String paramQuery){
        
    	String requestURL=null;

			//try {
				requestURL = endPoint +"recommendations/recommendationsSet/"+ paramQuery;//URLEncoder.encode(paramQuery, "UTF-8");
			/*} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			
		    String response = "";
		    BufferedReader rd = null;
		    try {
		        URL url = new URL(requestURL);
		        URLConnection conn2 = url.openConnection();
		        //conn2.setRequestProperty("Accept", "application/rdf+xml");
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

public String runQueryContext(String paramQuery){
    
	String requestURL=null;

		//try {
			requestURL = endPoint +"recommendations/contextualizedRecommendationsSet"+ paramQuery;//URLEncoder.encode(paramQuery, "UTF-8");
		/*} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		
	    String response = "";
	    BufferedReader rd = null;
	    try {
	        URL url = new URL(requestURL);
	        URLConnection conn2 = url.openConnection();
	        //conn2.setRequestProperty("Accept", "application/rdf+xml");
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

public String getPossibleContext(String paramQuery){
    
	String requestURL=null;

		//try {
			requestURL = endPoint +"contexts/recommendationContext"+ paramQuery;//URLEncoder.encode(paramQuery, "UTF-8");
		/*} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		
	    String response = "";
	    BufferedReader rd = null;
	    try {
	        URL url = new URL(requestURL);
	        URLConnection conn2 = url.openConnection();
	        //conn2.setRequestProperty("Accept", "application/rdf+xml");
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

public void setContext(String userURI, ArrayList<String> resources) {
	ClientConfig config = new DefaultClientConfig();
	Client client = Client.create(config);
	WebResource service = client.resource(getBaseURI());
	
	String[] context = resources.toArray(new String[resources.size()]);
	MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
	queryParams.put ("resource", Arrays.asList(context));
	
	/*System.out.println(*/
	service.path("recommender").path("contexts")
	.path("recommendationContext").queryParam("user", userURI)
	.queryParams(queryParams)
	.accept(MediaType.APPLICATION_XML).put(ClientResponse.class);
    //);

}

private URI getBaseURI() {
	return UriBuilder.fromUri(
			"http://calatola.man.poznan.pl/epnoiServer/rest").build();
}

}
