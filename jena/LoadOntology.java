package jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.util.FileManager;

/**
 * @author markus
 */

public class LoadOntology {
	
	public static String ontologyLocation = "C:\\data\\ontologies\\ontologyrdf_3_2_2015_1.owl";
	public static String ontologySource =  "http://www.semanticweb.org/markus.walden/ontologies/2014/10/organization";
	
	public static void testJena(String file) {
		FileManager.get().addLocatorClassLoader(
				LoadOntology.class.getClassLoader());
		System.out.println("Load Jena filemanager");
		Model model = null;
		if (file.length() == 0 || file == null) {
			model = null;
			System.out.println("xml/rdf file was not loaded: return");
			return;
		} else {
			model = FileManager.get().loadModel(file);
		}

		NodeIterator iter = model.listObjects();
		while (iter.hasNext()) {
			System.out.println("  " + iter.next().toString());
		}
		System.out.println("\n\nChange the notation: RDF/JSON");
		//model.write(System.out, "RDF/JSON" );
		//model.write(System.out, "TURTLE" );
		model.write(System.out, "JSON-LD" );
	}
}
