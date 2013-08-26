package com.talentica.semanticweb.utils;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public abstract class Utils {
	public static void printStatements(Resource r){
		StmtIterator listProperties = r.listProperties();
		while (listProperties.hasNext()){
			Statement s = listProperties.next();
			System.out.println(s);
		}
	}
}
