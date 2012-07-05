package rdf;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
 
@Provider 
public class JaxbContextResolver implements ContextResolver<JAXBContext> { 

	private JAXBContext context; 
	private Class[] types = { ReturnList.class, Adjacencies.class, Info.class, Item.class, Link.class, Output.class, Relation.class, Resources.class }; 
	
	public JaxbContextResolver() throws Exception { 
		this.context = new JSONJAXBContext(JSONConfiguration.mapped().arrays("adjacencies").build(), types); 
	} 
	
	public JAXBContext getContext(Class<?> objectType) { 
		return context; 
	} 
} 