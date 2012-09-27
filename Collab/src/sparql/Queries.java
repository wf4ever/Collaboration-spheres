package sparql;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Queries {

	private String prefix= "PREFIX mecontrib: <http://rdf.myexperiment.org/ontologies/contributions/> " +
			"PREFIX mebase: <http://rdf.myexperiment.org/ontologies/base/> " +
			"PREFIX sioc: <http://rdfs.org/sioc/ns#> " +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ";	
	
	public String getWorkflows(String user){
		return prefix + "SELECT ?wf WHERE { ?wf sioc:has_owner <"+user+">.?wf a mecontrib:Workflow}ORDER BY ?wf";
	}
	
	public String getFriends(String user){
		return prefix+ "SELECT ?friend WHERE {<"+user+"> mebase:has-friendship ?fs. ?fs mebase:accepted-at ?x"+
		"{?fs mebase:has-requester <"+user+">. ?fs mebase:has-accepter ?friend} " +
		"UNION " +
		"{?fs mebase:has-requester ?friend. ?fs mebase:has-accepter <"+user+">}}ORDER BY ?friend";
	}
	
	public String getName(String user){
		return prefix + "Select ?name where{<"+user+"> sioc:name ?name}";
	}
	
	public String getType (String source){
		return prefix + "Select ?type where{<"+source+"> a ?type}";
	}
	
	public String getWfsOfFriends(String user){
		return prefix + "SELECT DISTINCT ?wf WHERE "+
				"{<"+user+"> mebase:has-friendship ?fs. ?fs mebase:accepted-at ?x "+
				"{?fs mebase:has-requester <"+user+">. ?fs mebase:has-accepter ?friend} "+
				"UNION "+
				"{?fs mebase:has-requester ?friend. ?fs mebase:has-accepter <"+user+">} "+
				"{ ?wf sioc:has_owner ?friend.?wf a mecontrib:Workflow} "+
				"}ORDER BY ?wf";
	}
	
	public String getFriendsOfFriends(String user){
		return prefix + "SELECT DISTINCT ?friendOfFriend WHERE "+
				"{{<"+user+"> mebase:has-friendship ?fs. ?fs mebase:accepted-at ?x "+
				"{?fs mebase:has-requester <"+user+">.?fs mebase:has-accepter ?friend} "+
				"UNION "+
				"{?fs mebase:has-requester ?friend. ?fs mebase:has-accepter <"+user+">}}. "+
				"{{?friend mebase:has-friendship ?fs2. ?fs2 mebase:accepted-at ?z "+
				"{?fs2 mebase:has-requester ?friend.?fs2 mebase:has-accepter ?friendOfFriend} "+
				"UNION "+
				"{?fs2 mebase:has-requester ?friendOfFriend. ?fs2 mebase:has-accepter ?friend} "+
				"}}}ORDER BY ?friendOfFriend";
	}
	
	public String getTopViewed(int top){
		return prefix + "SELECT DISTINCT ?wf WHERE  "+
				"{?wf a mecontrib:Workflow. "+
				"?wf <http://rdf.myexperiment.org/ontologies/viewings_downloads/viewed> ?views "+
				"}ORDER BY DESC (xsd:int(?views)) LIMIT "+Integer.toString(top);
	}
	
	public String getOwner(String resource){
		return prefix + "Select ?owner where{<"+resource+"> sioc:has_owner ?owner}";
	}
	
//////////////////////////////////////////////////////////////////
	private String uriBase = "http://www.myexperiment.org";
	private ClientConfig config = new DefaultClientConfig();
	private Client client = Client.create(config);
	private WebResource service = client.resource(getBaseURI());

	private URI getBaseURI() {
		//el contexto del servicio
		return UriBuilder.fromUri(
				uriBase).build();
	}
	public String getFriendsREST(String user){
		String numUser=user.substring(user.lastIndexOf("/")+1);
		user="http://www.myexperiment.org/user.xml?id="+numUser+"&elements=friends";
		return service.path("user.xml").queryParam("id", numUser).queryParam("elements","friends").accept(MediaType.TEXT_XML).get(String.class);
	}
	
	public String getWfsREST(String user){
		String numUser=user.substring(user.lastIndexOf("/")+1);
		user="http://www.myexperiment.org/user.xml?id="+numUser+"&elements=workflows";
		return service.path("user.xml").queryParam("id", numUser).queryParam("elements","workflows").accept(MediaType.TEXT_XML).get(String.class);
	}
	
	
}
