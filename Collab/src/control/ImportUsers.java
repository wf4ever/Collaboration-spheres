package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import rdf.VarExtractor;
import sparql.Queries;
import sparql.RunQuery;
import sparql.RunRecommender;

public class ImportUsers {
	
	private Queries q;
	private RunQuery rq;
	private RunRecommender rec;
	private VarExtractor extractor;
	private ArrayList<String> friends;
	private ArrayList<String> wfs;
	private ArrayList<String> inner;
	private ArrayList<String> intermediate;
	private ArrayList<String> outter;
	private String type;
	private String name;
	private String uri;

	public ImportUsers(){
		q= new Queries();
		rq= new RunQuery();
		rec=new RunRecommender();
		extractor= new VarExtractor();
	}
	
	public void getUser(String uri){
		//init
		friends=new ArrayList<String>();
		wfs=new ArrayList<String>();
		//extract
		//identify type
		this.setType(uri);
		//get name
		this.setName(uri);
		//get arrays
		this.getWorkflows(uri);
		this.getFriends(uri);

	}
	
	public void getUser(List<String> resources) {
		//llenar el original (pos1 del array)
		fillCenter(resources.get(0));
		//llenar inner
		fillInner(resources);
		//el intermedio -> recursos de los del inner
		fillIntermediate();
		//el outter (top 10)
		fillOuter();
		
	}

	private void fillIntermediate() {
		intermediate= new ArrayList<String>();
		int numRecursos=18/inner.size();
		if (numRecursos==0) numRecursos=1;
		for (String user:inner){
			//if user.getType==wf entonces se añader su creador a la lista de friends
			if (getTypeResource(user).equals("Workflow")){
				addCreator(user,intermediate);
			}else{
			getIntermediateRecommendation(user, numRecursos);
			}
		}
		
	}

	private void getIntermediateRecommendation(String user, int numRecursos) {
		String rdf = rec.runQuery(recommendGeneral(user,numRecursos));
		rdf=extractor.lineSpliter(rdf, "resource");
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		int contador=0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereanceXML(line, "resource")) {
				input = extractor.complexExtract(line,"resource");
				if(contador<numRecursos && !appears(input,intermediate)&& !appears(input,inner)){
					  intermediate.add(input);
					  contador++;
					  }
			}
		}	
		
	}

	private void fillOuter() {
		outter= new ArrayList<String>();
		String rdf = rec.runQuery(recommendGeneral(this.uri,25));
		rdf=extractor.lineSpliter(rdf, "resource");
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereanceXML(line, "resource")) {
				input = extractor.complexExtract(line,"resource");
				outter.add(input);
			}
		}	
		
	}

	private String recommendGeneral(String uri,int num){
		return "user/"+extractID(uri)+"?max="+num;
	}
	
	private String extractID(String uri) {
		String aux=uri.substring(uri.lastIndexOf("/")+1);
		return aux;
	}

	/*private void fillIntermediate() {
		intermediate= new ArrayList<String>();
		ArrayList<String> fr=new ArrayList<String>();
		ArrayList<String> wf=new ArrayList<String>();
		int numRecursos=9/inner.size();
		if (numRecursos==0) numRecursos=1;
		for (String user:inner){
			//if user.getType==wf entonces se añader su creador a la lista de friends
			if (getTypeResource(user).equals("Workflow")){
				addCreator(user,fr);
			}else{
			getIntermediateFriends(user, fr, numRecursos);
			getIntermediateWorkflows(user, wf, numRecursos);
			}
		}
		for(String f: fr)intermediate.add(f);
		for(String w: wf)intermediate.add(w);
	}*/

	private void addCreator(String res, ArrayList<String> fr) {
		String rdf=rq.runQuery(q.getOwner(res));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "owner")){
			  input=extractor.SimpleExtract(line);
			  if(!appears(input,fr)&& !appears(input,inner)){
			  fr.add(input);
			  }
		  }
		}
		
	}

	private void getIntermediateWorkflows(String user, ArrayList<String> wf,
			int numRecursos) {
		String rdf=rq.runQuery(q.getWorkflows(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		int contador=0;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "wf")){
			  input=extractor.SimpleExtract(line);
			  if(contador<numRecursos && !appears(input,wf)&& !appears(input,inner)){
			  wf.add(input);
			  contador++;
			  }
		  }
		}
		
	}

	private void getIntermediateFriends(String user, ArrayList<String> fr, int numRecursos) {
		String rdf=rq.runQuery(q.getFriends(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		int contador=0;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "friend")){
			  input=extractor.SimpleExtract(line);
			  if(contador<numRecursos && !appears(input,fr) && !appears(input,inner) && !input.equals(this.uri)){
			  fr.add(input);
			  contador++;
			  }
		  }
		}
		
	}

	private boolean appears(String input, ArrayList<String> list) {
		boolean appears=false;
		int cont=0;
		while(!appears && cont<list.size()){
			if (list.get(cont).equals(input)){
				appears=true;
			}
			cont++;
		}
		return appears;
	}

	/*private void fillOuter() {
		outter= new ArrayList<String>();
		String rdf = rq.runQuery(q.getTopViewed(25));
		Scanner scanner = new Scanner(rdf);
		String line = null;
		String input = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (extractor.checkAppereance(line, "wf")) {
				input = extractor.SimpleExtract(line);
				outter.add(input);
			}
		}	
	}*/

	private void fillInner(List<String> resources) {
		inner=new ArrayList<String>();
		for(int i=1; i<resources.size(); i++){
			inner.add(resources.get(i));
		}
	}
	
	

	private void fillCenter(String uri) {
		this.setType(uri);
		this.uri=uri;
		//get name
		this.setName(uri);
	}

	private void getFriends(String user) {
		String rdf=rq.runQuery(q.getFriends(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "friend")){
			  input=extractor.SimpleExtract(line);
			  friends.add(input);
		  }
		}
		
	}

	private void getWorkflows(String user) {
		String rdf=rq.runQuery(q.getWorkflows(user));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "wf")){
			  input=extractor.SimpleExtract(line);
			  wfs.add(input);
		  }
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
	
	public String getTypeResource(String uri) {
		String result="";
		String rdf=rq.runQuery(q.getType(uri));
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "type")){
			  input=extractor.SimpleExtract(line);
			  int corte=input.lastIndexOf("/");
			  result= input.substring(corte+1, input.length());
		  }
		}
		return result;
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
	
	public ArrayList<String> getFriends(){
		return friends;
	}

	public ArrayList<String> getWorkflows(){
		return wfs;
	}

	public ArrayList<String> getInner() {
		return inner;
	}

	public ArrayList<String> getIntermediate() {
		return intermediate;
	}

	public ArrayList<String> getOutter() {
		return outter;
	}
}
