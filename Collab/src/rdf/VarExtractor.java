package rdf;

import java.util.ArrayList;

public class VarExtractor {
	
	public String SimpleExtract(String line){
		String result=null;
		
		int begin=line.indexOf("<uri>")+5;
		int end=line.lastIndexOf("</uri>");
		result = line.substring(begin, end);
		
		return result;
	}
	
	public boolean checkAppereance(String line, String param){
		return line.contains("<binding name=\""+param+"\">");
	}
	
	public boolean checkAppereanceXML(String line, String param){
		return line.contains("<"+param+">");
	}
	
	public String DatatypeExtract(String line){
		String result=null;
		int begin=line.indexOf("<literal datatype=\"http://www.w3.org/2001/XMLSchema#string\">")+60;
		int end=line.lastIndexOf("</literal>");
		result = line.substring(begin, end);
		
		return result;
	}
	
	public String lineSpliter(String line, String word){
		String aux=null;
		String[] lines=line.split("<"+word+">");
		for(int i=1; i<lines.length; i++){
			String s=lines[i];
			aux=aux+"<"+word+">"+s+"\n";
		}
		return aux;
	}
	
	public String complexExtract(String line,String word){
		String result=null;
		
		int begin=line.indexOf("<"+word+">")+word.length()+2;
		int end=line.lastIndexOf("</"+word+">");
		result = line.substring(begin, end);
		
		return result;
	}

}
