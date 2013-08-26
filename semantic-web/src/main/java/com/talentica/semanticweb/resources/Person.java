package com.talentica.semanticweb.resources;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.vocabulary.XSD;
import com.talentica.semanticweb.ontology.OntologyFactory;

public class Person implements Vocabulary{
	public static final String label = "Person";
	public static final String URI = BASE_URI + label;
	public static final String FULLNAME = URI + "#FullName";
	public static final String FIRSTNAME = URI + "#FirstName";
	public static final String SECONDNAME = URI + "#SecondName";
	
	protected String firstName;
	protected String secondName;
	
	public Person(String firstName, String secondName) {
		this.firstName = firstName;
		this.secondName = secondName;
	}

	public String getUri(){
		return URI + "/" + hashCode();
	}
	
	public Resource getRDFResource(){
		OntModel vocab = OntologyFactory.getTalenticaOntology();
		Resource person = vocab.createResource(getUri());
		person.addProperty(vocab.getProperty(FULLNAME), this.firstName + " " + this.secondName);
		person.addProperty(vocab.getProperty(FIRSTNAME), this.firstName);
		person.addProperty(vocab.getProperty(SECONDNAME), this.secondName);
		return person;
	}
	
	public static void addVocab(OntModel model) {
		OntClass ontPerson = model.createClass(URI);

		DatatypeProperty fullName = model.createDatatypeProperty(FULLNAME);
		fullName.addDomain(ontPerson);
		fullName.addRange(XSD.xstring);
		fullName.addSameAs(VCARD.FN);
		
		DatatypeProperty firstName = model.createDatatypeProperty(FIRSTNAME);
		firstName.addDomain(ontPerson);
		firstName.addRange(XSD.xstring);
		firstName.addSameAs(VCARD.Given);
		
		DatatypeProperty secondName = model.createDatatypeProperty(SECONDNAME);
		secondName.addDomain(ontPerson);
		secondName.addRange(XSD.xstring);
		secondName.addSameAs(VCARD.Family);
		
		ontPerson.addLabel(label, null);
		ontPerson.addSameAs(OntologyFactory.getFoafModel().getOntClass(FOAF + label));
	}

}
