package com.talentica.semanticweb.resources;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;
import com.talentica.semanticweb.ontology.OntologyFactory;

public class Employee extends Person{
	public static final String label = "Employee";
	public static final String URI = BASE_URI + label;
	public static final String DESIGATION = URI + "#DESIGNATION";
	private String designation;
	
	
	public Employee(String firstName, String secondName, String designation) {
		super(firstName, secondName);
		this.designation = designation;
	}

	public String getUri(){
		return super.getUri();
	}
	
	public Resource getRDFResource(){
		OntModel vocab = OntologyFactory.getTalenticaOntology();
		Resource employee = vocab.createResource(getUri());
		employee.addProperty(vocab.getProperty(DESIGATION), this.designation );
		employee.addProperty(vocab.getProperty(Person.FIRSTNAME), super.firstName);
		employee.addProperty(vocab.getProperty(Person.SECONDNAME), super.secondName);
		return employee;
	}
	
	public static void addVocab(OntModel model) {
		OntClass ontEmployee = model.createClass(URI);

		DatatypeProperty designation = model.createDatatypeProperty(DESIGATION);
		designation.addDomain(ontEmployee);
		designation.addRange(XSD.xstring);
		
		ontEmployee.addLabel(label, null);
		ontEmployee.addSuperClass(model.getResource(Person.URI));
	}

}
