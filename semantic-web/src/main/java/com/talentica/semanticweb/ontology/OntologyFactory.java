package com.talentica.semanticweb.ontology;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.talentica.semanticweb.resources.Person;
import com.talentica.semanticweb.resources.Price;
import com.talentica.semanticweb.resources.Ticket;
import com.talentica.semanticweb.resources.Vocabulary;

public class OntologyFactory {
	public static OntModel getTalenticaOntology(){
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
		Person.addVocab(model);
//		Employee.addVocab(model);
		
		Price.addVocab(model);
		Ticket.addVocab(model);
		return model;
	}
	
	public static OntModel getFoafModel(){
		OntDocumentManager docMgr = new OntDocumentManager();
		docMgr.addAltEntry(Vocabulary.FOAF, "file:/home/sush/code/Jena/jena-arq/Vocabularies/FOAF.rdf");
		OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_DL_MEM_RDFS_INF );
		spec.setDocumentManager(docMgr);
		OntModel m = ModelFactory.createOntologyModel(spec);
		m.read(Vocabulary.FOAF);
		return m;
	}
	
	public static void main(String[] args){
		getFoafModel().write(System.out);
//		getTalenticaOntology().write(System.out);
	}
	
}
