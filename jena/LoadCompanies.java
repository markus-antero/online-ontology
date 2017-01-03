package jena;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import jena.domain.KeyStats;

/**
General search
	#Company
		#companySymbol 
		#companyName

	#hasCompetitor
		#Company (Instance = symbol)

 * @author markus
 */

public class LoadCompanies extends LoadOntology {
	
	private ArrayList <KeyStats> keyStats;
	private Map <String, String> instanceValues;
	
	private LoadOntology ontologies;
	private String ontology;
	
	public LoadCompanies() {
		this.ontologies = new LoadOntology();
		this.keyStats = new ArrayList<KeyStats>();
		this.instanceValues = new HashMap <String, String>();
		this.model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
	}
	
	public OntModel model;
	
	public final String oCompany = LoadOntology.ontologySource +  "#Company";
	public final String oGics = LoadOntology.ontologySource + "#Gics";
	public final String oCorpDetails = LoadOntology.ontologySource + "#CorporateFunds";
	public final String oCompetitor = LoadOntology.ontologySource + "#Competitor";

	public void generateClassInstanceWithValues(String path, Map<String, String> c) {
		
        FileManager.get().readModel( this.model, path );
		
        OntClass company = model.getOntClass( this.oCompany);
        OntClass corp = model.getOntClass( this.oCorpDetails);
        OntClass gics = model.getOntClass( this.oGics);
        
        Individual individualCompany = company.createIndividual();
        Individual individualCorp = corp.createIndividual();
		Individual individualGics = gics.createIndividual();
		
		ExtendedIterator<OntProperty> it1 = company.listDeclaredProperties();
		while(it1.hasNext()){
            OntProperty p = it1.next();
            if (p.getLocalName().contains("hasBalance")){
            	ObjectProperty hasResult = p.asObjectProperty();
                Statement stmtHasRecord = model.createStatement(individualCompany, hasResult, individualCorp);
                model.add(stmtHasRecord);
                System.out.println("Statement for individual: " + stmtHasRecord);
            }
            if (p.getLocalName().contains("hasGics")){
            	ObjectProperty hasResult = p.asObjectProperty();
                Statement stmtHasRecord = model.createStatement(individualCompany, hasResult, individualGics);
                model.add(stmtHasRecord);
                System.out.println("Statement for individual: " + stmtHasRecord);
            }
        }
		
		ExtendedIterator<DatatypeProperty> it4 = model.listDatatypeProperties();
		while (it4.hasNext()){
			DatatypeProperty d = it4.next();
			if (c.containsKey(d.getLocalName())){
				System.out.println("\nData Property Name: "+ d.getLocalName());
	            System.out.println("Domain: "+ d.getDomain().getLocalName());
	        
	        //company
	            if (d.getLocalName().contains("companySymbol") || d.getLocalName().contains("companyName") 
						|| d.getLocalName().contains("companyCountry") ){
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualCompany, d, literal);
	            	model.add(stmt);
	            }
	            else if (d.getLocalName().contains("companyListingYear") ) {
	            	//Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDint);
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualCompany, d, literal);
	            	model.add(stmt);
	            }
	        
	        //corporateFunds
	            else if (d.getLocalName().contains("corpEnterpriseValue ") ){
	            	//Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDint);
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualCorp, d, literal);
	            	model.add(stmt);
	            }
	            else if (d.getLocalName().contains("corpMarketCapital") || d.getLocalName().contains("corpReturnOnAssets") || 
	            		d.getLocalName().contains("corpRevenuePerShare") || d.getLocalName().contains("corpTotalDeptEquite") || d.getLocalName().contains("corpPriseSales")){
	            	//Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDfloat);
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualCorp, d, literal);
	            	model.add(stmt);
	            }
	        
	        //Gics
	            else if (d.getLocalName().contains("gicsSector") || d.getLocalName().contains("gicsIndustry") 
	            		|| 	d.getLocalName().contains("gicsIndustrT")){
	            	Literal literal = model.createTypedLiteral(c.get(d.getLocalName()), XSDDatatype.XSDstring);
	            	Statement stmt = model.createStatement(individualGics, d, literal);
	            	model.add(stmt);
	            }
			}
		}	
	}

	/**
	 * #Company
	private String symbol;				#companySymbol
	private String name;				#companyName
	private String Country;				#companyCountry 
	private Integer IPOyear;			#companyListingYear

#hasGics
	private String Sector;				#gicsSector	
	private String Industry;			#gicsIndustryT
	private String summary;				#gicsIndustry

#hasBalance
	private Integer EnterpriseValue;	#corpEnterpriseValue 
	private Integer prices;				#corpPriseSales 
	private Float ReturnonAssets;		#corpReturnOnAssets
	private Float RevenuePerShare;		#corpRevenuePerShare 
	private Float TotalCashPerShare;	#corpMarketCapital
	private Float TotalDebtEquity;		#corpTotalDeptEquite
	
//Stock missing 
	 * Missing link to competitor
	 * @param path
	 * @param c
	 * @return
	 */
	public Map <String, String> createMapForOntology (KeyStats c ) {
		Map <String, String> ontology = new HashMap <String, String>(); 
		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
		ontology.put("companySymbol", c.getSymbol());
		ontology.put("companyName", c.getName());
		ontology.put("companyCountry", c.getCountry());
		ontology.put("companyListingYear", String.valueOf(c.getIPOyear()));
		
		ontology.put("gicsSector", c.getSector());
		ontology.put("gicsIndustrT", c.getIndustry());
		ontology.put("gicsIndustry", c.getSummary());
		
		ontology.put("corpEnterpriseValue", String.valueOf(c.getEnterpriseValue()));
		ontology.put("corpPriseSales", String.valueOf(c.getPrices()));		
		ontology.put("corpReturnOnAssets", String.valueOf(c.getReturnonAssets()));
		ontology.put("corpRevenuePerShare", String.valueOf(c.getRevenuePerShare()));
		ontology.put("corpMarketCapital", String.valueOf(c.getTotalCashPerShare()));
		ontology.put("corpTotalDeptEquite", String.valueOf(c.getTotalDebtEquity()));
		
		return ontology;
	}

	/**
	 * container details - dataset	
	//Symbol	Name		LastSale	MarketCap		ADR TSO		Country	IPOyear		Sector	Industry								Summary Quote						EnterpriseValue		PriceSales	
	//MMM		3M Company	NULL		106830909149.82	n/a			United States		NULL	Health Care	Medical/Dental Instruments	http://www.nasdaq.com/symbol/mmm	109770000000		3.36
	 
	 * to variables
	String symbol, String name, String country, Integer iPOyear, String sector, String industry, String summary, Integer enterpriseValue, Integer prices, Float returnonAssets,  Float revenuePerShare, Float totalCashPerShare, Float totalDebtEquity)
	 */
	public static void main (String [] args)
	{
		System.out.println("main");
		LoadCompanies  p = new LoadCompanies ();
		
		p.ontology = LoadOntology.ontologyLocation;
				
		KeyStats c = new KeyStats("", "", "", null, "", "", "", null, null, null, null, null, null);
				
		Map <String, String> ontology = p.createMapForOntology(c); 
		System.out.println(ontology);
			
		p.generateClassInstanceWithValues(p.ontology, ontology);		
	}

}
