package fr.lirmm.agroportal.OWLApiExample;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTransformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOntology {






	public static void main(String[] args) {
		IRI IOR = IRI.create("http://owl.api.tutorial");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o=null;
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass person = df.getOWLClass(IOR+"#Person");
		OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(person);
		o.add(da);
		//System.out.println(o);
		OWLClass woman = df.getOWLClass(IOR+"#Woman");
		OWLSubClassOfAxiom w_sub_p = df.getOWLSubClassOfAxiom(woman, person);
		o.add(w_sub_p);
		//System.out.println(o);
		OWLClass A = df.getOWLClass(IOR + "#A");
		OWLClass B = df.getOWLClass(IOR + "#B");
		OWLClass X = df.getOWLClass(IOR + "#X");
		OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#S");
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));
		o.add(ax);
		System.out.println("Exemplo de Substituicao - Antes");
		o.logicalAxioms().forEach(System.out::println);

		final Map<OWLClassExpression, OWLClassExpression> replacements = new HashMap<>();

		o.logicalAxioms().forEach(System.out::println);

		replacements.put(df.getOWLObjectSomeValuesFrom(R, A), X);

		OWLObjectTransformer<OWLClassExpression> replacer =
				new OWLObjectTransformer<>((x) -> true, (input) -> {
			OWLClassExpression l = replacements.get(input);
			if (l == null) {
				return input;
			}
			return l;
		} , df, OWLClassExpression.class);

		List<OWLOntologyChange> results = replacer.change(o);
		o.applyChanges(results);
		System.out.println("Depois");
		o.logicalAxioms().forEach(System.out::println);

		// exemplo de filtro dentro da funcao

		System.out.println("Filtro - tipo 1");
		o.signature().filter((e->(!e.isBuiltIn()&&e.getIRI().getFragment().startsWith("P")))).
		forEach(System.out::println);

		System.out.println("Filtro - tipo 2");
		o.signature().filter(e->!e.isBuiltIn()&&e.getIRI().getRemainder().orElse("").startsWith
				("P")).forEach(System.out::println);

		// Annotations

		OWLClass student = df.getOWLClass(IRI.create(IOR + "#ID879812719872"));
		OWLAnnotation commentAnno = df.getOWLAnnotation(df.getRDFSComment(), df.
				getOWLLiteral("Class representing all Students in the University", "en"));
		OWLAnnotation labelAnno = df.getOWLAnnotation(df.getRDFSLabel(), df.
				getOWLLiteral("Student", "en"));
		OWLAxiom ax1 = df.getOWLAnnotationAssertionAxiom(student.getIRI(), labelAnno);
		man.applyChange(new AddAxiom(o, ax1));
		OWLAxiom ax2 = df.getOWLAnnotationAssertionAxiom(student.getIRI(),
				commentAnno);
		man.applyChange(new AddAxiom(o, ax2));

		System.out.println("Ontologia com anotacoes");
		o.logicalAxioms().forEach(System.out::println);




		File fileout = new File("/home/abrahao/data/downloads/tutorial_local.owl");
		File fileout2 = new File("/home/abrahao/data/downloads/tutorial_local.rdf");
		File fileout3 = new File("/home/abrahao/data/downloads/tutorial_local.obo");

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
