package com.talentica.semanticweb.resources;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;
import com.talentica.semanticweb.ontology.OntologyFactory;

public class Ticket implements Vocabulary{
	public static final String label = "Ticket";
	public static final String URI = BASE_URI + label;
	public static final String OWNER = URI + "#Owner";
	public static final String DOJ = URI + "#DOJ"; 
	public static final String TO = URI + "#To";
	public static final String FROM = URI + "#From";
	public static final String PRICE = URI + "#Price";
	public static final String MOT = URI + "#MOT";

	private Person owner;
	private String doj;
	private String to;
	private String from;
	private Price price;
	private String mot;
	
	public String getUri(){
		return URI + "/" + hashCode();
	}
	
	public Ticket(Person owner, String doj, String to, String from, Price price,
			String mot) {
		this.owner = owner;
		this.doj = doj;
		this.to = to;
		this.from = from;
		this.price = price;
		this.mot = mot;
	}

	public Resource getRDFResource(){
		OntModel vocab = OntologyFactory.getTalenticaOntology();
		Resource ticket = vocab.createResource(getUri());
		ticket.addProperty(vocab.getProperty(OWNER), this.owner.getRDFResource());
		ticket.addProperty(vocab.getProperty(DOJ), this.doj);
		ticket.addProperty(vocab.getProperty(TO), this.to);
		ticket.addProperty(vocab.getProperty(FROM), this.from);
		ticket.addProperty(vocab.getProperty(PRICE), this.price.getRDFResource());
		ticket.addProperty(vocab.getProperty(MOT), this.mot);
		return ticket;
	}
	
	public static void addVocab(OntModel model) {
		OntClass ontTicket = model.createClass(URI);
		ObjectProperty owner = model.createObjectProperty(OWNER);
		owner.addDomain(ontTicket);
		owner.addRange(model.getOntClass(Person.URI));

		ObjectProperty doj = model.createObjectProperty(DOJ);
		doj.addDomain(ontTicket);
		doj.addRange(XSD.xstring);
		
		DatatypeProperty to = model.createDatatypeProperty(TO);
		to.addDomain(ontTicket);
		to.addRange(XSD.xstring);
		
		DatatypeProperty from = model.createDatatypeProperty(FROM);
		from.addDomain(ontTicket);
		from.addRange(XSD.xstring);
		
		ObjectProperty price = model.createObjectProperty(PRICE);
		price.addDomain(ontTicket);
		price.addRange(model.getOntClass(Price.URI));
		
		DatatypeProperty mot = model.createDatatypeProperty(MOT);
		mot.addDomain(ontTicket);
		mot.addRange(XSD.xstring);
		ontTicket.addLabel(label, null);
	}
}
