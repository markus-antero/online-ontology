package jena.domain;

/**
 * @author markus
 */
 
public class Registry {
	
	private String user;
	private String pass;
	private String email;
	private String region;
	private String industry;
	
	public Registry (String industry, String region, 
			String email, String pass, String user){
		this.user = user;
		this.pass = pass;
		this.email = email;
		this.region = region;
		this.industry = industry;
	}
	
	public String toString(){
		return "user: " + this.user + 
				"\nindustry: " + this.industry +
				"\nregion: " + this.region + 
				"\nemail: " + this.email;
	}

	public String getPass() {
		return pass;
	}
	public String getUser() {
		return user;
	}
	public String getEmail() {
		return email;
	}
	public String getRegion() {
		return region;
	}
	public String getIndustry() {
		return industry;
	}
}
