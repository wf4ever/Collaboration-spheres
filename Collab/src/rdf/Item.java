package rdf;

import java.util.ArrayList;
import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "item")
@XmlType (propOrder={"name","type","uri","inner","intermediate","outter"})
public class Item {
	
	private String name;
	private String type;
	private String uri;
	private List<Adjacencies> inner;
	private List<Adjacencies> intermediate;
	private List<Adjacencies> outter;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<Adjacencies> getInner() {
		return inner;
	}
	public void setInner(List<Adjacencies> inner) {
		this.inner = inner;
	}
	public List<Adjacencies> getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(List<Adjacencies> intermediate) {
		this.intermediate = intermediate;
	}
	public List<Adjacencies> getOutter() {
		return outter;
	}
	public void setOutter(List<Adjacencies> outter) {
		this.outter = outter;
	}
	
	public void fill(String aux, String name, String type, ArrayList<String> inner, ArrayList<String> intermediate, ArrayList<String> outter) {
		setName(name);
		setType(type);
		setUri(aux);
		fillInner(inner);
		fillIntermediate(intermediate);
		fillOutter(outter);
	}
	
	private void fillInner(ArrayList<String> inner) {
		this.inner = new ArrayList<Adjacencies>();
		Adjacencies adj= fillAdjacencies(inner);
		this.inner.add(adj);
	}
	
	private void fillIntermediate(ArrayList<String> intermediate) {
		this.intermediate = new ArrayList<Adjacencies>();
		Adjacencies adj= fillAdjacencies(intermediate);
		this.intermediate.add(adj);
	}
	
	private void fillOutter(ArrayList<String> outter) {
		this.outter = new ArrayList<Adjacencies>();
		Adjacencies adj= fillAdjacencies(outter);
		this.outter.add(adj);
	}
	
	private Adjacencies fillAdjacencies(ArrayList<String> elements) {
		Adjacencies adj=new Adjacencies();
		List<Link> list=new ArrayList<Link>();
		for (String s: elements){
		Link l=new Link();
		l.setLinkedTo(s);
		list.add(l);}
		adj.setAdjacencies(list);
		return adj;
	}

	


}
