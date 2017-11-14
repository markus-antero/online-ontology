package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.DBConnection;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected String connection;   
    /**
     * Add to init file
     * @see HttpServlet#HttpServlet()
     * jdbc:sqlserver://localhost:1433;" +
			"database=ServiceProject;" +
			"user=markus;" +
            "password=mg1Mtw8427!\"
     */
    public ServletLogin() {
        super();
        this.connection = "jdbc:sqlserver://localhost:1433;" +
				"database=ServiceProject;" +
				"user=markus;" +
				"password=mg1Mtw8427!\"";
         
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
               
        String query = 
        		"SELECT [username],[password] "
        		+ "FROM [ServiceProject].[dbo].[registry] "
        		+ "WHERE [username] like ? and [password] like ? ";
        
        
		
        DB db1 = new DB();
        Connection dbConnection = db1.dbConnect("jdbc:sqlserver://localhost:1433;" +
											"database=ServiceProject;" +
											"user=markus;" +
                							"password=mg1Mtw8427!\"" );
        PreparedStatement prepStmt = null;
        try {
			//db = new DBConnection(this.connection);
			
			prepStmt = dbConnection.prepareStatement(query);
        	prepStmt.setString(1, user);
        	prepStmt.setString(2, pwd);
        	ResultSet rs = prepStmt.executeQuery();
        
			if (rs.next()) {
				HttpSession session = request.getSession();
	            session.setAttribute("user",user);
	            session.setMaxInactiveInterval(180*60);
	            
	            Cookie userName = new Cookie("user", user);
	            userName.setMaxAge(30*60);
	            
	            response.addCookie(userName);
	            response.sendRedirect(request.getHeader("referer"));
			}
				//write user into login
			else{
				//login name and password did not match
				response.sendRedirect(request.getHeader("referer"));
			}
		} catch (SQLException e1) {
			System.err.println("SQL Statement went wrong: login");
			response.sendRedirect(request.getHeader("referer"));
		}
	 	finally {
	 		try {
	 			if (prepStmt != null) {prepStmt.close(); }
	 			if (dbConnection != null && !dbConnection.isClosed()) {
	 				dbConnection.close();
	 			}
	 		} catch (SQLException ex) {
	 			ex.printStackTrace();
	 		}
	 	}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * ,
		“username”:”Pekka”,
		"region_name":"Finland",
		"industry":"XYZ",
		"Password"
		"session_id":"123"
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("loginName");
        String pwd = request.getParameter("pwd");
        String region = request.getParameter("regionName");
        String industry = request.getParameter("industry");
        String email = request.getParameter("email");
        
        String stmt = 
        		"INSERT INTO [ServiceProject].[dbo].[registry]"
        		+ "([username],[password],[email],[region],[industry]) "
        		+ "VALUES (?, ?, ?, ?, ?)";
        
        DB db1 = new DB();
        Connection conn = db1.dbConnect("jdbc:sqlserver://localhost:1433;" +
											"database=ServiceProject;" +
											"user=markus;" +
                							"password=mg1Mtw8427!\"" );
        PreparedStatement prepStmt = null;;
		try {
			
			prepStmt = conn.prepareStatement(stmt);
			prepStmt.setString(1, user);
			prepStmt.setString(2, pwd);
			prepStmt.setString(3, region);
			prepStmt.setString(4, industry);
			prepStmt.setString(5, email);
			prepStmt.executeUpdate();
		} catch (SQLException e1) {
			System.err.println("SQL Statement went wrong: register user");
			response.sendRedirect(request.getHeader("referer"));
		}       
		finally {
	 		try {
	 			if (prepStmt != null) {prepStmt.close(); }
	 			if (conn != null && !conn.isClosed()) {
	 				conn.close();
	 			}
	 		} catch (SQLException ex) {
	 			ex.printStackTrace();
	 		}
	 	}
        response.sendRedirect(request.getHeader("referer"));
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
