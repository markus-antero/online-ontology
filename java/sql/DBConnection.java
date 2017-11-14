package sql;

import java.sql.*;

public class DBConnection{
    
	public String connectionString;
	public Connection conn;
	
	public static String query = 
    		"SELECT [username],[password] "
    		+ "FROM [ServiceProject].[dbo].[registry] "
    		+ "WHERE [username] like 'markus' and [password] like 'markus' ";
	
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
	
	public static void main(String[] args) throws SQLException{       
        DB db = new DB();
        Connection dbConnection = db.dbConnect("jdbc:sqlserver://localhost:1433;" +
											"database=ServiceProject;" +
											"user=markus;" +
                							"password=mg1Mtw8427!\"" );
        System.out.println(dbConnection.toString());
        Statement stmt = null;
        try {
        	stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(DBConnection.query3);
            
            while (rs.next()) {
            	String company = rs.getString(1);
            	String competitor = rs.getString(2);
            	String name = rs.getString(3);
            	
//            	String symbol = rs.getString(1);
//            	String name = rs.getString(2);
//            	String country = rs.getString(3);
//            	Integer iPOyear = rs.getInt(4);
//            	String sector = rs.getString(5);
//            	String industry = rs.getString(6);
//            	String summary = rs.getString(7);
//            
//            //Line 2
//            	Integer enterpriseValue = rs.getInt(8);
//            	Integer prices = rs.getInt(9);
//            	Float returnonAssets = rs.getFloat(10);
//            	Float revenuePerShare = rs.getFloat(11);
//            	Float totalCashPerShare = rs.getFloat(12);
//            	Float totalDebtEquity = rs.getFloat(13);
//                
//            //Line 3
//            	String date = rs.getString(14);
//            	Float open = rs.getFloat(15);
//            	Float high = rs.getFloat(16);
//            	Float low = rs.getFloat(17);
//            	Float close = rs.getFloat(18);
//            	Integer volume = rs.getInt(19);
                
                System.out.println(rs.toString() );
            }
        } catch (SQLException e ) {
            
        } finally {
            if (stmt != null) { stmt.close(); }
        }       
        try {
			dbConnection.close();
		} catch (SQLException e) {	}
    }
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