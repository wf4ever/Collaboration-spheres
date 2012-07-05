package rdf;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import control.Element;

@XmlRootElement(name = "list")
@XmlType (propOrder={"name","type","uri","owner","friends","ros"})
public class Resources {
	
	private String name;
	private String type;
	private String uri;
	private List<Relation> owner;
	private List<Relation> friends;
	private List<Relation> ros;


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

	public List<Relation> getOwner() {
		return owner;
	}
	public void setOwner(List<Relation> owner) {
		this.owner = owner;
	}
	public List<Relation> getFriends() {
		return friends;
	}
	public void setFriends(List<Relation> friends) {
		this.friends = friends;
	}
	public List<Relation> getRos() {
		return ros;
	}
	public void setRos(List<Relation> ros) {
		this.ros = ros;
	}
	
	public void fill(String aux, String name, String type, ArrayList<Element> friends, ArrayList<Element> wfs) {
		setName(name);
		setType(type);
		setUri(aux);
		fillOwner(aux);
		fillFriends(friends);
		fillRos(wfs);
	}
	
	private void fillOwner(String user) {
		owner = new ArrayList<Relation>();
		ArrayList<Element> elements=new ArrayList<Element>();
		Element e=new Element(user);
		elements.add(e);
		Relation adj= fillRelation(elements);
		owner.add(adj);
	}
	
	private void fillFriends(ArrayList<Element> friends) {
		this.friends = new ArrayList<Relation>();
		Relation adj= fillRelation(friends);
		this.friends.add(adj);
	}
	
	private void fillRos(ArrayList<Element> wfs) {
		this.ros = new ArrayList<Relation>();
		Relation adj= fillRelation(wfs);
		this.ros.add(adj);
	}
	
	private Relation fillRelation(ArrayList<Element> elements) {
		Relation adj=new Relation();
		List<Info> list=new ArrayList<Info>();
		for (Element e: elements){
		Info inf=new Info();
		inf.setLinkedTo(e.getUri());
		inf.setDirect(e.isDirect());
		inf.setIndirect(e.isIndirect());
		inf.setRecent(e.isRecent());
		inf.setOther(e.isOther());
		list.add(inf);}
		adj.setRelation(list);
		return adj;
	}

}
