package sql;

import java.sql.*;

public class DBConnection{
    
	public String connectionString;
	public Connection conn;
		
	public static String query2 =
			" SELECT  c.[Symbol],[Name],[Country],[IPOyear],[Sector],[Industry],[Summary Quote] "
					+ ",[EnterpriseValue] ,[PriceSales] ,[ReturnonAssets] ,[RevenuePerShare],[TotalCashPerShare],[TotalDebtEquity] "
					+ ",[Date],[Open],[High],[Low],[Close],[Volume]"
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
							+ ") stock ON stock.Symbol = k.symbol ;";
	
	public static String query3 =
			"SELECT [companySymbol] ,[competitorSymbol] ,[Name]  "
	        		+ "FROM [ServiceProject].[dbo].[dcompanyDetails] as c "
	        			+ "inner join [ServiceProject].[dbo].[semiCompetitor] as s "
	        				+ "ON c.Symbol = s.competitorSymbol  "
	        		+ "where s.companySymbol like ? ";
	
	public DBConnection (String connectionURI) throws SQLException
	{	
		this.connectionString = connectionURI;
		this.conn = DriverManager.getConnection(connectionURI); 
	}	
	public String getConnectionString() {
		return connectionString;
	}
	public Connection getConn() {
		return conn;
	}

class DB{
    public Connection dbConnect(  String db_connect_string ){
    	Connection conn = null;
    	try{    		
    		Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );
            conn = DriverManager.getConnection(db_connect_string); 
 
            System.out.println( "connected" );
        }
        catch( Exception e ){
            e.printStackTrace();
        }
        return conn;
    }
};