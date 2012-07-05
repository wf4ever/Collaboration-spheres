package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rdf.Item;
import rdf.ReturnList;
import control.ImportUsers;

@Path("/getSpheres")
public class RestSpheres {
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainText() {
		return "Hello Jersey";
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSpheresInfo(@QueryParam("inner") List<String> resources) {
		if (resources==null)resources=new ArrayList<String>();
		while (resources.size()<2)resources.add("");
		 ImportUsers iu=new ImportUsers(); 
		 iu.getUser(resources); 
		 ArrayList<String>inner=iu.getInner(); 
		 ArrayList<String> intermediate=iu.getIntermediate();
		 ArrayList<String> outter=iu.getOutter();
		 Item item=new Item();
		 item.fill(resources.get(0),iu.getName(),iu.getType(),inner,intermediate, outter);
		 List<Item> list = new ArrayList<Item>(); 
		 list.add(item);
		 ReturnList rL=new ReturnList();
		 rL.setList(list);
		return Response.ok(rL).build();
	}

}
