package jena.domain;

/**
 * @author markus
 */
 
public class CompanyNames {
	private String symbol;
	private String name;
	
	public CompanyNames(String symbol, String name) {
		super();
		this.symbol = symbol;
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyNames [symbol=" + symbol + ", name=" + name + "]";
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}
	
	
	
}
