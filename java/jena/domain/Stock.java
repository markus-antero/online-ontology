package jena.domain;

public class Stock {
	private String Date;
	
	private Float Open;
	private Float High;
	private Float Low;
	private Float Close;
	private Integer Volume;
	
	
	public Stock(String date, Float open, Float high, Float low,
			Float close, Integer volume ) {
		super();
		Date = date;
		Open = open;
		High = high;
		Low = low;
		Close = close;
		Volume = volume;
		
	}
		
	@Override
	public String toString() {
		return "Stock [Date=" + Date + ", Open=" + Open + ", High=" + High
				+ ", Low=" + Low + ", Close=" + Close + ", Volume=" + "Volume";
	}

	public String getDate() {
		return Date;
	}
	public Float getOpen() {
		return Open;
	}
	public Float getHigh() {
		return High;
	}
	public Float getLow() {
		return Low;
	}
	public Float getClose() {
		return Close;
	}
	public Integer getVolume() {
		return Volume;
	}
}
