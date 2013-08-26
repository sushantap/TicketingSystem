package com.talentica.semanticweb;

import java.io.InputStream;

import org.apache.jena.atlas.io.IndentedWriter;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	public static final String URI = "http://www.talentica.com/";
	public static final String ontoURI = "www.sush.org/ontology/";
	public static final String FOAF = "http://xmlns.com/foaf/0.1/";
	public static final String personURI    = "http://www.sush.org/SushantPradhan";
	public static final String fullName     = "Sushant Pradhan";
	public static final String NL = System.getProperty("line.separator") ;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

//    public void testInference()
//    {
//        OntModel schema = ModelFactory.createOntologyModel();
//		ObjectProperty parent = schema.createObjectProperty(URI + "Parent");
//		ObjectProperty pop = schema.createObjectProperty(URI + "Pop");
//		ObjectProperty mum = schema.createObjectProperty(URI + "Mum");
//		parent.addSubProperty(pop);
//		parent.addSubProperty(mum);
//		
//		Model dataModel = ModelFactory.createDefaultModel();
//		Resource ravi = dataModel.createResource(URI + "Ravi");
//		ravi.addProperty(pop, pop);
//		ravi.addProperty(mum, mum);
//		
//		InfModel infModel = ModelFactory.createRDFSModel(schema, dataModel);
//		Resource inferedRavi = infModel.getResource(URI + "Ravi"); 
//		StmtIterator listStatements = inferedRavi.listProperties();
//		while(listStatements.hasNext()){
//			System.out.println(listStatements.next());
//		}
//    }
//    public void testOntology()
//    {
//    	OntDocumentManager docMgr = new OntDocumentManager();
//		docMgr.addAltEntry(ontoURI, "file:/home/sush/code/Jena/jena-arq/Vocabularies/FOAF.rdf");
//		OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_DL_MEM_RDFS_INF );
//		spec.setDocumentManager(docMgr);
//		OntModel m = ModelFactory.createOntologyModel(spec);
//		m.read(ontoURI);
//		OntClass Person = m.getOntClass(FOAF+"Person");
//		StmtIterator listProperties = Person.listProperties();
//		while (listProperties.hasNext()){
//			System.out.println(listProperties.next());
//		}
//		Resource person = Person.createIndividual();
//		person.addProperty(VCARD.FN, "sushanta pradhan");
//		Model rdfModel = ModelFactory.createDefaultModel();
//		rdfModel.add(person.listProperties());
//		rdfModel.write(System.out);
//    }
    public void testSparQl(){
		String query1 = "SELECT * {?s ?p ?o }" ;
		executeQuery(query1);

//String query2 = "SELECT ?x ?y WHERE {?x vcard:FN ?y}";
//executeQuery(query2);
//
//String query3 = "SELECT ?x ?z WHERE {?x vcard:N ?z}";
//executeQuery(query3);

//String query4 = "SELECT ?y ?givenName WHERE  { "
//		+ "?y  vcard:Family  'Smith' . "
//		+ "?y  vcard:FN  ?givenName . "
//		+ "}";
//executeQuery(query4);

//String query5 = "PREFIX info: <http://somewhere/peopleInfo#>"
//		+ "SELECT ?age ?name ?fn ?sn WHERE  {"
//		+ "?x info:age ?age ."
//		+ "?x vcard:FN ?name ."
////		+ "?x vcard:Family ?sn ."
////		+ "?x vcard:Given ?fn ."
//		+ "FILTER (?age < 24)"
//		+ "}";
//executeQuery(query5);

//String query6 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> PREFIX info: <http://somewhere/peopleInfo#>"
//		+ "SELECT ?name ?age WHERE {"
//		+ "{ [] foaf:name ?name } UNION { [] vcard:FN ?name }."
//		+ " ?person vcard:FN  ?name ."
//		+ "OPTIONAL { ?person info:age ?age . FILTER ( ?age > 24 ) }"
//		+ "}";
//executeQuery(query6);

//String query7 = "PREFIX info: <http://somewhere/peopleInfo#>"
//		+ "SELECT ?name ?age WHERE{"
//		+ "?person vcard:FN  ?name ."
//		+ "OPTIONAL { ?person info:age ?age . }"
//		+ "FILTER ( !bound(?age) || ?age > 24 )"
//		+ "}";
//executeQuery(query7);
    }
    private void executeQuery(String queryString) {
		System.out.println("###########################################");
		String prolog = "PREFIX vcard: <"+VCARD.getURI()+">";
		String queryStr = prolog + queryString;
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open("/home/sush/code/experiments/vc-db-3.rdf");
        model.read(in,null);
		Query query = QueryFactory.create(queryStr) ;
        query.serialize(new IndentedWriter(System.out,true)) ;
//        System.out.println() ;
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet rs = qexec.execSelect();
        for ( ; rs.hasNext() ; )
        {
            QuerySolution rb = rs.nextSolution();
			System.out.println(rb);
        }
	}
}

