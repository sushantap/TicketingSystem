package com.talentica.semanticweb;

import junit.framework.TestCase;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.talentica.semanticweb.resources.Person;
import com.talentica.semanticweb.utils.Utils;

/**
 * Unit test for simple App.
 */
public class InferenceTest extends TestCase
{
	public static final String URI = "http://www.talentica.com/";

    public void testInference()
    {
    	System.out.println("####################testInference");
        OntModel schema = ModelFactory.createOntologyModel();
		ObjectProperty parent = schema.createObjectProperty(URI + "Parent");
		ObjectProperty pop = schema.createObjectProperty(URI + "Pop");
		ObjectProperty mum = schema.createObjectProperty(URI + "Mum");
		parent.addSubProperty(pop);
		parent.addSubProperty(mum);
		
		Model dataModel = ModelFactory.createDefaultModel();
		Resource ravi = dataModel.createResource(URI + "Ravi");
		ravi.addProperty(pop, pop);
		ravi.addProperty(mum, mum);
		
		InfModel infModel = ModelFactory.createRDFSModel(schema, dataModel);
		Resource inferedRavi = infModel.getResource(URI + "Ravi"); 
		StmtIterator listStatements = inferedRavi.listProperties();
		while(listStatements.hasNext()){
			System.out.println(listStatements.next());
		}
    }
    public void testInferenceForPerson()
    {
    	System.out.println("####################testInferenceForPerson");
    	OntModel schema = ModelFactory.createOntologyModel();
    	Person.addVocab(schema);
    	Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
    	reasoner = reasoner.bindSchema(schema);
    	
    	Model data = ModelFactory.createDefaultModel();
    	Resource ravi = data.createResource(URI + "Ravi");
//    	ravi.addProperty(RDF.type, person);
    	ravi.addProperty(schema.getProperty(Person.FIRSTNAME), "sushant");
        ravi.addProperty(schema.getProperty(Person.SECONDNAME), "pradhan");
    	Utils.printStatements(ravi);
    	System.out.println("####################");
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
        Resource inferredRavi = infmodel.getResource(URI + "Ravi");
        Utils.printStatements(inferredRavi);
    	
    }
}

