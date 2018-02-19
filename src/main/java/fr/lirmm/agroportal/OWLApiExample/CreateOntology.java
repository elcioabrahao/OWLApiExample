package fr.lirmm.agroportal.OWLApiExample;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTransformer;

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
		System.out.println("Antes");
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
		System.out.println("Antes");
		o.logicalAxioms().forEach(System.out::println);


	}

}
