package com.talentica.semanticweb.resources;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;
import com.talentica.semanticweb.ontology.OntologyFactory;

public class Price implements Vocabulary{
	public static final String label = "Price";
	public static final String URI = BASE_URI + label;
	public static final String AMNT_URI = URI + "#amount";
	public static final String CURRENCY_URI = URI + "#Currency";
	
	private float amount;
	private String currency;
	
	public Price(float amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	public String getUri(){
		return URI + "/" + hashCode();
	}
	
	public Resource getRDFResource(){
		OntModel vocab = OntologyFactory.getTalenticaOntology();
		Resource price = vocab.createResource(getUri());
		price.addProperty(vocab.getProperty(AMNT_URI), String.valueOf(this.amount));
		price.addProperty(vocab.getProperty(CURRENCY_URI), this.currency);
		return price;
	}
	
	public static void addVocab(OntModel model) {
		OntClass ontCurrency = model.createClass(URI);
		DatatypeProperty AMNT = model.createDatatypeProperty(AMNT_URI);
		DatatypeProperty CURRENCY = model.createDatatypeProperty(CURRENCY_URI);
		AMNT.addDomain(ontCurrency);
		AMNT.addRange(XSD.xfloat);
		CURRENCY.addDomain(ontCurrency);
		CURRENCY.addRange(XSD.xstring);
		ontCurrency.addLabel(label, null);
	}
}
