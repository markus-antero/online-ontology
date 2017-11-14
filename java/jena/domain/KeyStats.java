package jena.domain;

import java.util.ArrayList;

/**
 * removed fields
 * ,[LastSale;
 * 
 * @author markus
 *
 */

public class KeyStats {
	
	private String symbol;
	private String name;
	private String Country;
	private Integer IPOyear;
	private String Sector;
	private String Industry;
	private String summary;
		      
	private Integer EnterpriseValue;
	private Integer prices;
	private Float ReturnonAssets;
	private Float RevenuePerShare;
	private Float TotalCashPerShare;
	private Float TotalDebtEquity;
	
	public ArrayList <Stock> stocks; 
	
	public KeyStats(String symbol, String name, String country, Integer iPOyear,
			String sector, String industry, String summary,
			Integer enterpriseValue, Integer prices, Float returnonAssets,
			Float revenuePerShare, Float totalCashPerShare,
			Float totalDebtEquity) {
		super();
		this.symbol = symbol;
		this.name = name;
		Country = country;
		IPOyear = iPOyear;
		Sector = sector;
		Industry = industry;
		this.summary = summary;
		EnterpriseValue = enterpriseValue;
		this.prices = prices;
		ReturnonAssets = returnonAssets;
		RevenuePerShare = revenuePerShare;
		TotalCashPerShare = totalCashPerShare;
		TotalDebtEquity = totalDebtEquity;
		stocks = new ArrayList<Stock>();
	}
	
	@Override
	public String toString() {
		return "KeyStats [symbol=" + symbol + ", name=" + name + ", Country="
				+ Country + ", IPOyear=" + IPOyear + ", Sector=" + Sector
				+ ", Industry=" + Industry + ", summary=" + summary
				+ ", EnterpriseValue=" + EnterpriseValue + ", prices=" + prices
				+ ", ReturnonAssets=" + ReturnonAssets + ", RevenuePerShare="
				+ RevenuePerShare + ", TotalCashPerShare=" + TotalCashPerShare
				+ ", TotalDebtEquity=" + TotalDebtEquity + "]";
	}
	
	public String getSymbol() {
		return symbol;
	}
	public String getName() {
		return name;
	}
	public String getCountry() {
		return Country;
	}
	public Integer getIPOyear() {
		return IPOyear;
	}
	public String getSector() {
		return Sector;
	}
	public String getIndustry() {
		return Industry;
	}
	public String getSummary() {
		return summary;
	}
	public Integer getEnterpriseValue() {
		return EnterpriseValue;
	}
	public Integer getPrices() {
		return prices;
	}
	public Float getReturnonAssets() {
		return ReturnonAssets;
	}
	public Float getRevenuePerShare() {
		return RevenuePerShare;
	}
	public Float getTotalCashPerShare() {
		return TotalCashPerShare;
	}
	public Float getTotalDebtEquity() {
		return TotalDebtEquity;
	}
		      

}
