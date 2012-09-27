package control;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import rdf.VarExtractor;
import sparql.Queries;
import sparql.RunQuery;

public class ImportLists {
	
	private Queries q;
	private RunQuery rq;
	private VarExtractor extractor;
	private ArrayList<Element> friends;
	private ArrayList<Element> wfs;
	private String type;
	private String name;
	
	public ImportLists(){
		q= new Queries();
		rq= new RunQuery();
		extractor= new VarExtractor();
	}
	
	public void getList(String uri){
		//init
		friends=new ArrayList<Element>();
		wfs=new ArrayList<Element>();
		//extract
		//identify type
		this.setType(uri);
		//get name
		this.setName(uri);
		//get Indirect (el grupo más grande primero para hacerlo eficiente)
		this.getIndirectWorkflows(uri);
		this.getIndirectFriends(uri);
		//get Direct
		this.getDirectWorkflows(uri);
		this.getDirectFriends(uri);
		//get FAKE recent
		this.setFAKErecent(5);
		//get Surprises!
		this.getSurpriseWf(15);
		
		

	}

	private void getSurpriseWf(int i) {
		ArrayList<Element> ind = new ArrayList<Element>();
		String rdf = rq.runQuery(q.getTopViewed(i));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereance(line, "wf")) {
				input = extractor.SimpleExtract(line);
				Element e = new Element(input);
				e.setOther(true);
				ind.add(e);
			}
		}
		for (Element el : ind) {
			int pos = 0;
			boolean found = false;
			String uri = el.getUri();
			while (pos < wfs.size() && !found) {
				//existe
				if (wfs.get(pos).getUri().compareTo(uri) == 0) {
					found = true;
					wfs.get(pos).setOther(true);
				//tenemos una posición
				} else if (wfs.get(pos).getUri().compareTo(uri) > 0) {
					found = true;
					wfs.add(pos, el);
				} else
					pos++;
			}
			//al final del array
			if (!found)
				wfs.add(el);
		}//fin del for
		
	}

	private void setFAKErecent(int i) {
		int wfsSelected=(i*wfs.size())/100;
		int friendsSelected=(i*friends.size())/100;
		Random r=new Random();
		int aux=0;
		for (int j=0; j<wfsSelected; j++){
			aux=r.nextInt(wfs.size())-1;
			wfs.get(aux).setRecent(true);
		}
		
		for (int j=0; j<friendsSelected; j++){
			aux=r.nextInt(friends.size())-1;
			friends.get(aux).setRecent(true);
		}
		
	}

	private void getIndirectFriends(String user) {
		ArrayList<Element> ind = new ArrayList<Element>();
		String rdf = rq.runQuery(q.getFriendsOfFriends(user));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereance(line, "friendOfFriend")) {
				input = extractor.SimpleExtract(line);
				Element e = new Element(input);
				e.setIndirect(true);
				ind.add(e);
			}
		}
		for (Element el: ind)friends.add(el);
		
	}

	private void getIndirectWorkflows(String user) {
		ArrayList<Element> ind = new ArrayList<Element>();
		String rdf = rq.runQuery(q.getWfsOfFriends(user));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereance(line, "wf")) {
				input = extractor.SimpleExtract(line);
				Element e = new Element(input);
				e.setIndirect(true);
				ind.add(e);
			}
		}
		for (Element el: ind)wfs.add(el);
	}

	private void getDirectFriends(String user) {
		ArrayList<Element> ind = new ArrayList<Element>();
		// USING API //
		/*String rdf = rq.runQuery(q.getFriendsREST(user));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereanceResource(line)) {
				input = extractor.resourceExtract(line);
				Element e = new Element(input);
				e.setDirect(true);
				ind.add(e);
			}
		}*/
		//USING SPARQL//
		String rdf = rq.runQuery(q.getFriends(user));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereance(line, "friend")) {
				input = extractor.SimpleExtract(line);
				Element e = new Element(input);
				e.setDirect(true);
				ind.add(e);
			}
		}

		// evitar duplicados
		for (Element el : ind) {
			int pos = 0;
			boolean found = false;
			String uri = el.getUri();
			while (pos < friends.size() && !found) {
				//existe
				if (friends.get(pos).getUri().compareTo(uri) == 0) {
					found = true;
					friends.get(pos).setDirect(true);
				//tenemos una posición
				} else if (friends.get(pos).getUri().compareTo(uri) > 0) {
					found = true;
					friends.add(pos, el);
				} else
					pos++;
			}
			//al final del array
			if (!found)
				friends.add(el);
		}//fin del for
		
	}

	private void getDirectWorkflows(String user) {
		ArrayList<Element> ind = new ArrayList<Element>();
		// USING API //
		/*String rdf=rq.runQuery(q.getWfsREST(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereanceResource(line)){
			  input=extractor.resourceExtract(line);
			  Element e= new Element(input);
			  e.setDirect(true);
			  ind.add(e);
		  }
		}*/
		// USING SPARQL //
		String rdf=rq.runQuery(q.getWorkflows(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "wf")){
			  input=extractor.SimpleExtract(line);
			  Element e= new Element(input);
			  e.setDirect(true);
			  ind.add(e);
		  }
		}
		
		//evitar duplicados
		for (Element el: ind){
			int pos=0; boolean found=false;
			String uri=el.getUri();
			while (pos<wfs.size()&& !found){
				if (wfs.get(pos).getUri().compareTo(uri)==0){
					found=true;
					wfs.get(pos).setDirect(true);
				}
				else if(wfs.get(pos).getUri().compareTo(uri)>0){
					found=true;
					wfs.add(pos, el);
				}
				else pos++;
			}
			
			if (!found)wfs.add(el);
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String uri) {
		String rdf=rq.runQuery(q.getType(uri));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "type")){
			  input=extractor.SimpleExtract(line);
			  int corte=input.lastIndexOf("/");
			  type= input.substring(corte+1, input.length());
		  }
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String uri) {
		String rdf=rq.runQuery(q.getName(uri));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "name")){
			  input=extractor.DatatypeExtract(line);
			  name= input;
		  }
		}
	}
	
	public ArrayList<Element> getFriends(){
		return friends;
	}

	public ArrayList<Element> getWorkflows(){
		return wfs;
	}

}
