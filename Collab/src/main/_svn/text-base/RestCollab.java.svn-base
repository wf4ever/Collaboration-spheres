package main;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rdf.Resources;

import control.Element;
import control.ImportLists;

@Path("/getCollab")
public class RestCollab {
	
	// This method is called if TEXT_PLAIN is request
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String sayPlainTextHello() {
			return "Hello Jersey";
		}

		/*// This method is called if XML is request
		/@GET
		@Produces(MediaType.TEXT_XML)
		public String sayXMLHello() {
			return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
		}

		// This method is called if HTML is request
		@GET
		@Produces(MediaType.TEXT_HTML)
		public String sayHtmlHello() {
			return "<html> " + "<title>" + "Hello Jersey" + "</title>"
					+ "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
		}*/
		
		// This method is called if JSON is request
		/*@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String sayHello() {
			ImportUsers iu=new ImportUsers();
			String aux=iu.getUser("http://www.myexperiment.org/users/18");
					return aux;
				}*/
		
		/*@GET
		@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public Output getJSON() {
			Output out = new Output();
			out.setInner("inner");
			out.setUser("Marco");
			return out;
		}*/
		
		@GET
		@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public List<Resources> getJSONs(@QueryParam("userIni")@DefaultValue("") String user) {
			ImportLists il=new ImportLists();
			il.getList(user);
			ArrayList<Element> friends=il.getFriends();
			ArrayList<Element> wfs=il.getWorkflows();
			Resources res=new Resources();
			res.fill(user,il.getName(),il.getType(),friends,wfs);
			List<Resources> list = new ArrayList<Resources>();
			list.add(res);
			/*ImportUsers iu=new ImportUsers();
			iu.getUser(user);
			ArrayList<String> friends=iu.getFriends();
			ArrayList<String> wfs=iu.getWorkflows();
			Resources item=new Resources();
			item.fill(user,iu.getName(),iu.getType(),friends,wfs);
			List<Resources> list = new ArrayList<Resources>();
			list.add(item);*/
			/*Item item=new Item();
			item.fill(user,iu.getName(),iu.getType(),friends,wfs);
			List<Item> list = new ArrayList<Item>();
			list.add(item);*/
			return list;
		}

}
