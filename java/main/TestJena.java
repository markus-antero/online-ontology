package main;

import jena.LoadOntology;
import jena.ProcessRegistry;
import jena.domain.Registry;

public class TestJena {
	public static void main (String [] args)
	{
		String ontologyCorp = "C:\\data\\ontologies\\ontologyRDF.owl";
		String ontologyUser = "C:\\data\\ontologies\\UserLoginRDF.owl";
		TestJena.createInstances();
	}
	
	public static void createInstances (){
		String ontologyCorp = "C:\\data\\ontologies\\ontologyRDF.owl";
		String ontologyUser = "C:\\data\\ontologies\\UserLoginRDF.owl";
		
		String userP     = "userName";
		String passP     = "password";
		String emailP    = "email";
		String regionP   = "region";
		String industryP = "industry";
		
		Registry r = new Registry(Industry, region, 
			email, pass, user);
		
		ProcessRegistry p = new ProcessRegistry ();
		p.getInstanceValues().put(userP, user);
		p.getInstanceValues().put(passP, pass);
		p.getInstanceValues().put(emailP, email);
		p.getInstanceValues().put(regionP, region);
		p.getInstanceValues().put(industryP, Industry);
		
		String qToClass = "http://www.semanticweb.org/markus.walden/ontologies/2014/10/user#User";
		p.generateRIGWithValues(ontologyUser, qToClass, p.getInstanceValues());
		
	}
	