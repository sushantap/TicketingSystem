package com.talentica.semanticweb.app;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.RDF;
import com.talentica.semanticweb.ontology.OntologyFactory;
import com.talentica.semanticweb.resources.Person;
import com.talentica.semanticweb.resources.Price;
import com.talentica.semanticweb.resources.Ticket;


public class TicketingApp {
	public static void main(String[] args){
		OntModel schema = OntologyFactory.getTalenticaOntology();
    	Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
    	reasoner = reasoner.bindSchema(schema);
    	Model data = ModelFactory.createDefaultModel();
    	addRandomTicket(data);
    	addRandomTicket(data);
    	findTickets(schema, data, false);
    	InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
    	findTickets(schema, infmodel, true);
//    	validateModel(infmodel);
	}

	private static void validateModel(InfModel infmodel) {
		ValidityReport validity = infmodel.validate();
        if (validity.isValid()) {
            System.out.println("OK");
        } else {
            System.out.println("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext(); ) {
                ValidityReport.Report report = (ValidityReport.Report)i.next();
                System.out.println(" - " + report);
            }
        }
	}

	private static void findTickets(OntModel schema, Model data, boolean inferred) {
		StmtIterator tickets = data.listStatements(null, RDF.type, schema.getProperty(Ticket.URI));;
		if(inferred){
			System.out.println("###########searching inferred tickets##############");
		}
		else{
			System.out.println("###########searching non-inferred tickets##############");
		}
		while(tickets.hasNext()){
			Statement stmt = tickets.next();
			Resource ticket = stmt.getSubject();
			Statement owner = ticket.getProperty(schema.getProperty(Ticket.OWNER));
			if(inferred){
				System.out.println("Owner: " + owner.getProperty(schema.getProperty("http://www.w3.org/2001/vcard-rdf/3.0#FN")).getObject());
			}
			else{
				System.out.println("Owner: " + owner.getProperty(schema.getProperty(Person.FULLNAME)).getObject());
			}
			System.out.println("DOJ: " + ticket.getProperty(schema.getProperty(Ticket.DOJ)).getObject());
			System.out.println("FROM: " + ticket.getProperty(schema.getProperty(Ticket.FROM)).getObject());
			System.out.println("TO: " + ticket.getProperty(schema.getProperty(Ticket.TO)).getObject());
			System.out.println("MOT: " + ticket.getProperty(schema.getProperty(Ticket.MOT)).getObject());
		}
	}

	private static void addRandomTicket(Model model) {
		Person owner = new Person("sushant", "pradhan");
		String doj = Utils.getRandomDate().toString();
		String to = "Pune";
		String from = "Mumbai";
		Price price = new Price(100.56f, "INR");
		String mot = "RAIL";
		Ticket t1 = new Ticket(owner, doj, to, from, price, mot);
		model.add(t1.getRDFResource().listProperties());
		model.add(owner.getRDFResource().listProperties());
	}
}
