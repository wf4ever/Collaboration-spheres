package rdf;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Link {
	
	private String linkedTo;

	public String getLinkedTo() {
		return linkedTo;
	}

	public void setLinkedTo(String linkedTo) {
		this.linkedTo = linkedTo;
	}

}
