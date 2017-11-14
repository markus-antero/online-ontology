package jena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class ProcessRegistry {
	
	private ArrayList <String> request;
	private Map <String, String> instanceValues;
	private String ontology;
	
	public ProcessRegistry (){
		this.setRequest(new ArrayList <String>());	
		this.setInstanceValues(new HashMap <String, String>());		
	}
	
	public OntModel generateRIGWithValues (String path, String ClassUri, Map <String, String> c) {
		OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        FileManager.get().readModel( model, path );

        OntClass query = model.getOntClass( ClassUri );
		Individual individual = query.createIndividual();

		ExtendedIterator<DatatypeProperty> it = model.listDatatypeProperties();
		while(it.hasNext()){
			DatatypeProperty d  = it.next();
            if (c.containsKey(d.getLocalName())){     			
        		Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSD);
        		Statement stmt = model.createStatement(individual, d, literal);
        		model.add(stmt);
        		System.out.println("Statement for individual: " + stmt);
            }
        }   
		//model.write(System.out, "TURTLE" );
		return model;
	}

	
	public ArrayList<String> getRequest() {
		return request;
	}

	public void setRequest(ArrayList<String> request) {
		this.request = request;
	}

	public Map<String, String> getInstanceValues() {
		return instanceValues;
	}

	public void setInstanceValues(Map<String, String> instanceValues) {
		this.instanceValues = instanceValues;
	}

	public String getOntology() {
		return ontology;
	}

	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	
}
