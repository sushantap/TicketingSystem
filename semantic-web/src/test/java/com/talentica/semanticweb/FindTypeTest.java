package com.talentica.semanticweb;

import junit.framework.TestCase;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.talentica.semanticweb.resources.Person;
import com.talentica.semanticweb.utils.Utils;

/**
 * Unit test for simple App.
 */
public class FindTypeTest extends TestCase
{
	public static final String URI = "http://www.talentica.com/";
    /**
     * 
     */
    public void test()
    {
    	OntModel schema = ModelFactory.createOntologyModel();
    	Person.addVocab(schema);
    	Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
    	reasoner = reasoner.bindSchema(schema);
    	
    	Model data = ModelFactory.createDefaultModel();
    	
    	Resource ravi = data.createResource(URI + "Ravi");
    	ravi.addProperty(schema.getProperty(Person.FIRSTNAME), "sushant");
        ravi.addProperty(schema.getProperty(Person.SECONDNAME), "pradhan");
        
        
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
        Utils.printStatements(infmodel.getResource(URI + "Ravi"));
//        StmtIterator listStatements = infmodel.listStatements(null, RDF.type, infmodel.getResource(Person.URI));
//        StmtIterator listStatements = infmodel.listStatements(null, RDF.type, infmodel.getResource(Person.FOAF + "Person"));
//        while(listStatements.hasNext()){
//        	System.out.println(listStatements.next());
//        }
//        ValidityReport validity = infmodel.validate();
//        if (validity.isValid()) {
//            System.out.println("OK");
//        } else {
//            System.out.println("Conflicts");
//            for (Iterator i = validity.getReports(); i.hasNext(); ) {
//                ValidityReport.Report report = (ValidityReport.Report)i.next();
//                System.out.println(" - " + report);
//            }
//        }
    }
}

