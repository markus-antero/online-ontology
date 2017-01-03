package servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author markus
 */

public class Competitor {
	
	String companyId;
	public Map <String, ArrayList> competitor;
	public ArrayList <CompDetails> details;
	
	public Competitor() {
		this.competitor = new HashMap <String, ArrayList>();
		this.details = new ArrayList<CompDetails>();
	}
	
	public void setCompanyName(String id){ this.companyId = id;}
	
	private CompDetails addCompany (String competitor, String name){
		return new CompDetails(competitor, name);
	}
	
	public void addListOfcompetitors(String competitor, String name){
		this.details.add(this.addCompany(competitor, name));
	}
	
	public void makeList(){
		this.competitor.put(this.companyId, this.details);
	}
	
	@Override
	public String toString() {
		return "Competitor [companyId=" + companyId + ", competitor="
				+ competitor + ", details=" + details + "]";
	}
}

class CompDetails{
	String competitor;
	String name;
	
	public CompDetails(String competitor, String name) {
		
		this.competitor = competitor;
		this.name = name;
	}
	
};
