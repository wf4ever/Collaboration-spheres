package rdf;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Adjacencies {

	private List<Link> adjacencies;

	public List<Link> getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(List<Link> list) {
		this.adjacencies = list;
	}
	
	
}
