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

import jena.domain.CompanyNames;
import jena.domain.KeyStats;

public class LoadCompanyNames extends LoadOntology {
	
	private ArrayList <KeyStats> keyStats;
	private Map <String, String> instanceValues;
	
	private LoadOntology ontologies;
	private String ontology;
	
	public LoadCompanyNames() {
		this.ontologies = new LoadOntology();
		this.keyStats = new ArrayList<KeyStats>();
		this.instanceValues = new HashMap <String, String>();
		this.model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
	}
	
	public OntModel model;
	
	public final String oCompany = LoadOntology.ontologySource +  "#Company";
	
	public Map <String, String> createMapForOntology (CompanyNames comp) {
		Map <String, String> ontology = new HashMap <String, String>();
		ontology.put("companySymbol", comp.getSymbol());
		ontology.put("companyName", comp.getName());
		
		return ontology; 
	}
	
	public void generateClassInstanceWithValues(String path, Map<String, String> c) {	
        FileManager.get().readModel( this.model, path );
		
        OntClass company = model.getOntClass( this.oCompany);       
        Individual individualCompany = company.createIndividual();
        
        ExtendedIterator<DatatypeProperty> it4 = model.listDatatypeProperties();
		while (it4.hasNext()){
			DatatypeProperty d = it4.next();
			if (c.containsKey(d.getLocalName())){
				System.out.println("\nData Property Name: "+ d.getLocalName());
	            System.out.println("Domain: "+ d.getDomain().getLocalName());
        
	            if (d.getLocalName().contains("companySymbol") || d.getLocalName().contains("companyName") ){
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualCompany, d, literal);
	            	model.add(stmt);
	            }
			}
		}     
	}
	
	public static void main (String [] args)
	{
		System.out.println("main");
		LoadCompanyNames  p = new LoadCompanyNames ();
		
		p.ontology = LoadOntology.ontologyLocation;
		System.out.println("p.ontology: " + p.ontology);
	}	
}
