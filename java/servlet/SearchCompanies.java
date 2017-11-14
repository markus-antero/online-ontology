package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.ontology.OntModel;

import jena.LoadCompanies;
import jena.LoadCompanyNames;
import jena.LoadOntology;
import jena.domain.CompanyNames;
import jena.domain.KeyStats;
import jena.domain.Stock;
import sql.DBConnection;

/**
 * Servlet implementation class SearchCompanies
 */
@WebServlet("/SearchCompanies")
public class SearchCompanies extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public Competitor competitor; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCompanies() {
        super();
        this.competitor = new Competitor();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String query = "SELECT c.Symbol,[Name] "
				+ "FROM [ServiceProject].[dbo].[dcompanyDetails] as c "
				+ "inner join  [ServiceProject].[dbo].[keystats] as k "
				+ "ON c.Symbol = k.symbol order by c.Symbol;"; 
		
		//ServletLogin 
		DB db1 = new DB();
        Connection dbConnection = db1.dbConnect("jdbc:sqlserver://localhost:1433;" +
											"database=ServiceProject;" +
											"user=markus;" +
                							"password=mg1Mtw8427!\"" );
        Statement stmt = null;
        ArrayList <CompanyNames> comp = 
        		new ArrayList <CompanyNames>();
        
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String symbol = rs.getString(1);
                String name = rs.getString(2);
                comp.add(new CompanyNames(symbol, name));
            }
        } catch (SQLException e ) {           
        } finally {
        	try {
        		if (stmt != null) { stmt.close(); }
        		if (dbConnection != null && !dbConnection.isClosed()) {
        			dbConnection.close();
        		}
        	} catch (SQLException ex) {
        		ex.printStackTrace();
 			}
        }
        
        LoadCompanyNames load = new LoadCompanyNames();
        
        for (CompanyNames c : comp){
			Map <String, String> ontology = load.createMapForOntology(c);
			load.generateClassInstanceWithValues(LoadOntology.ontologyLocation, ontology);
		}
        OntModel ontmodel = load.model;
        ontmodel.write(System.out, "JSON-LD" );
        
        response.setContentType("application/json");
        ServletOutputStream output = response.getOutputStream();
        ontmodel.write(output, "JSON-LD");
        
        //this.doOutputStream(request, response, ontmodel);        
        //response.sendRedirect(request.getHeader("referer"));
	}

	/**
	 * *  Java class 	xsd type
		Float 		float
		Double 		double
		Integer 	int
		Long 		long
		Short 		short
		Byte 		byte
		BigInteger 	integer
		BigDecimal 	decimal
		Boolean 	Boolean
		String 		string
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String param = request.getParameter("search");		
		ArrayList <ArrayList<KeyStats>> companies = this.getCompanies(param);
		Competitor comp = this.competitor;
		System.out.println(comp);
		
		LoadCompanies load = new LoadCompanies();
	    for (ArrayList <KeyStats> a : companies){
	    	for (KeyStats k : a){
	    		System.out.println(k);
	    		Map <String, String> ontology = load.createMapForOntology(k);
				load.generateClassInstanceWithValues(LoadOntology.ontologyLocation, ontology);
	    	}
			
	    }				
			
        OntModel ontmodel = load.model;
        ontmodel.write(System.out, "JSON-LD" );
        System.out.println("done");
        
        response.setContentType("application/json");
        
        //PrintWriter out = response.getWriter();
        ServletOutputStream output = response.getOutputStream();
        ontmodel.write(output, "JSON-LD");
        
        //this.doOutputStream(request, response, ontmodel);
        
        //response.sendRedirect(request.getHeader("referer"));
	}
	
	public void doOutputStream (HttpServletRequest request, HttpServletResponse response, OntModel ontmodel ) throws UnsupportedEncodingException{
				
		try {
	        URL back = new URL(request.getHeader("referer"));
	        URLConnection urlConn = back.openConnection();
	        urlConn.setDoOutput(true);
	        OutputStreamWriter outputLine = new OutputStreamWriter(urlConn.getOutputStream());
	        ontmodel.write(outputLine, "JSON-LD");
	        //outputLine.write("");
	        outputLine.flush();


//	        // Get the response
//	        BufferedReader streamReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//	        String line;
//	        //streamReader = holding the data... can put it through a DOM loader?
//	        while ((line = streamReader.readLine()) != null) {
//	            PrintWriter writer = response.getWriter();
//	            writer.print(line);
//	        }
	        outputLine.close();
	        //streamReader.close();

	    } catch (MalformedURLException me) {
	        System.out.println("MalformedURLException: " + me);
	    } catch (IOException ioe) {
	        System.out.println("IOException: " + ioe);
	    }
		
		
		
	}
	
	/**
	 * comp.makeList(); Not collected
	 * Generate list of competitors
	 * @param param
	 * @return 
	 */
	private ArrayList <ArrayList<KeyStats>> getCompanies (String param){
		String param1 = param + " %";
		
		DB db1 = new DB();
        Connection dbConnection = db1.dbConnect("jdbc:sqlserver://localhost:1433;" +
											"database=ServiceProject;" +
											"user=markus;" +
                							"password=mg1Mtw8427!\"" );    
        String query = 
        		"SELECT [companySymbol] ,[competitorSymbol] ,[Name]  "
        		+ "FROM [ServiceProject].[dbo].[dcompanyDetails] as c "
        			+ "inner join [ServiceProject].[dbo].[semiCompetitor] as s "
        				+ "ON c.Symbol = s.competitorSymbol  "
        		+ "where s.companySymbol like ? ";
        
        PreparedStatement stmt = null;      
        
        ArrayList <ArrayList<KeyStats>> companies
        	= new ArrayList <ArrayList<KeyStats>>();
        
        Competitor comp = null;      
        int i = 1;
        try {
        	stmt = dbConnection.prepareStatement(query);
        	stmt.setString(1, param1);
        	
        	ResultSet rs = stmt.executeQuery();
        	comp = new Competitor();
        	
            companies.add(this.getCompanyDetails(param, dbConnection));
            while (rs.next()) {
            	//collect information about competitors 
            	String company = rs.getString(1).trim();
            	String competitor = rs.getString(2).trim();
            	String name = rs.getString(3);
             	           	
            	comp.setCompanyName(company);
            	comp.addListOfcompetitors(competitor, name);
            		
            	i = i + 1;
            	companies.add(this.getCompanyDetails(competitor, dbConnection));
            }            
            comp.makeList();
            System.out.println();
        } catch (SQLException e ) {           
        	System.err.println("SQL Statement went wrong: competitor " + e);
        } finally {
        	try {
        		if (stmt != null) { stmt.close(); }
        		if (dbConnection != null && !dbConnection.isClosed()) {
        			dbConnection.close();
        		}
        	} catch (SQLException ex) {
        		ex.printStackTrace();
 			}
        }        
        this.competitor = comp;
        return companies;
	}
	/**
	 * Generate detailed dataset		
	 * @param search
	 * @param conn
	 * @return
	 */
	private ArrayList <KeyStats> getCompanyDetails (String search, Connection conn)
	{
		String query = 
				" SELECT c.Symbol, [Name], [Country], [IPOyear], [Sector], [Industry], [Summary Quote] "
				+ ",[EnterpriseValue], [PriceSales], [ReturnonAssets], [RevenuePerShare], [TotalCashPerShare], [TotalDebtEquity] "
				+ ",[Date], [Open], [High], [Low], [Close], [Volume] "
				+ "FROM [ServiceProject].[dbo].[dcompanyDetails] as c  "
					+ "inner join  [ServiceProject].[dbo].[keystats] as k    "
						+ "ON 	c.Symbol = k.symbol 	   "
					+ "left join (	  "
						+ "select s.[Symbol],[Date],[Open],[High],[Low],[Close],[Volume],[Adj_Close]	"
						+ "from [ServiceProject].[dbo].[stockPrice] as s 	"
							+ "inner join ( "
								+ "select  [Symbol],max([Date]) as MaxDate	"
								+ "from [ServiceProject].[dbo].[stockPrice]  	"
								+ "group by [Symbol]	"
							+ ") sm on s.Symbol = sm.Symbol and s.[Date] = sm.MaxDate	"
						+ ") stock ON stock.Symbol = k.symbol "
						+ "WHERE c.Symbol like ? ;";
		
		PreparedStatement stmt = null;
        ArrayList<KeyStats> comp = 
        		new ArrayList <KeyStats>(); 
        
        KeyStats key = null;
        Stock stock = null;
        
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, search);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            
            //Line 1
            	String symbol = rs.getString(1);
            	String name = rs.getString(2);
            	String country = rs.getString(3);
            	Integer iPOyear = rs.getInt(4);
            	String sector = rs.getString(5);
            	String industry = rs.getString(6);
            	String summary = rs.getString(7);
            
            //Line 2
            	Integer enterpriseValue = rs.getInt(8);
            	Integer prices = rs.getInt(9);
            	Float returnonAssets = rs.getFloat(10);
            	Float revenuePerShare = rs.getFloat(11);
            	Float totalCashPerShare = rs.getFloat(12);
            	Float totalDebtEquity = rs.getFloat(13);
                
            //Line 3
            	String date = rs.getString(14);
            	Float open = rs.getFloat(15);
            	Float high = rs.getFloat(16);
            	Float low = rs.getFloat(17);
            	Float close = rs.getFloat(18);
            	Integer volume = rs.getInt(19);
            	
            	stock = new Stock(date, open, high, low, close, volume);
            	key = new KeyStats(symbol, name, country, iPOyear, sector, industry, summary, enterpriseValue, prices, returnonAssets, revenuePerShare, totalCashPerShare, totalDebtEquity);
            	key.stocks.add(stock);
            	
            	comp.add(key);
            }
        } catch (SQLException e ) {  
        	System.err.println("SQL Statement went wrong: companyDetails " + e);
        } finally {
        	try {
        		if (stmt != null) { stmt.close(); }
        	} catch (SQLException ex) {
        		ex.printStackTrace();
 			}
        }
		return comp;
	}
	
	public void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
}
