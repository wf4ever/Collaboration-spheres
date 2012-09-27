package main;

import rdf.VarExtractor;
import sparql.Queries;

public class pruebitas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Queries q=new Queries();
		System.out.println(q.getFriendsREST("http://www.myexperiment.org/users/18"));

		VarExtractor v=new VarExtractor();
		String a=v.resourceExtract("<friend uri=\"http://www.myexperiment.org/user.xml?id=162\" resource=\"http://www.myexperiment.org/users/162\">Yuwei Lin</friend>");
		System.out.println(a);
	}

}
