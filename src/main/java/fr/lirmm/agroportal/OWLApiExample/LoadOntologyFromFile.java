package fr.lirmm.agroportal.OWLApiExample;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class LoadOntologyFromFile {

	public static void main(String[] args) {
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o = null;
		
		File file = new File("/home/abrahao/data/downloads/pizza.owl");
		
		try {
		
			o = man.loadOntologyFromOntologyDocument(file);
		System.out.println(o);
		} catch (OWLOntologyCreationException e) {
		e.printStackTrace();
		}	

	
		
	}
	

}
