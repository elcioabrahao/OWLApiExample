package fr.lirmm.agroportal.OWLApiExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class LoadOntologyFromWeb {
	
	
public static void main(String[] args) {
		
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
	OWLOntology o = null ;
	try {
		o = man.loadOntology(pizzaontology);
	} catch (OWLOntologyCreationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(o);	
	
	System.out.println("Axioms: "+man.getOntologyFormat(o));
		

	File fileout = new File("/home/abrahao/data/downloads/pizza_local.owl");
	File fileout2 = new File("/home/abrahao/data/downloads/pizza_local.rdf");
	File fileout3 = new File("/home/abrahao/data/downloads/pizza_local.obo");

	try {
		man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout));
		man.saveOntology(o, new RDFXMLDocumentFormat(),new FileOutputStream(fileout2));
		man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout3));
		
	} catch (OWLOntologyStorageException | FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
