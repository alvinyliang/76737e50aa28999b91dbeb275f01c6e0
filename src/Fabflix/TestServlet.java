package Fabflix;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Logger.PerformanceLogger;

import java.sql.*;

public class TestServlet extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
		String title = request.getParameter("title");
		String config = request.getParameter("config");
	
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        
        
        try {
        	long startTime = System.nanoTime();
        	long endTime = System.nanoTime();
    		
        	startTime = System.nanoTime();
         	/////////////////////////////////
     		/// ** part to be measured ** ///
 			
        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        conn = dbConn.getConnection();
//	        conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);

	        
	        //Normal Statement
	        Statement stmt = conn.createStatement();
	        String SQL = "SELECT * from movies where title = '" + title + "';";
			ResultSet rs = stmt.executeQuery(SQL);
//			
			//Prepared Statement
//			PreparedStatement stmt = conn.prepareStatement("SELECT * from movies where title = ?");
//			stmt.setString(1, title);
//			ResultSet rs = stmt.executeQuery();
//			
			String message = "";
	        while (rs.next()){
	        	String dbtitle = rs.getString("title");
	        	String year = rs.getString("year");
	        	String director = rs.getString("director");
		        message = dbtitle + " " + year + " " + director;
	        }
	        
	      /// ** part to be measured ** ///
    		/////////////////////////////////
        	endTime = System.nanoTime();
        	long elapsedTime = endTime - startTime;
        	
        	String rootPath = getServletContext().getRealPath("/");
//			
        	PerformanceLogger.log(rootPath + "/logs/TestLog.log", 
    				elapsedTime, 1, "TJ", config, "", title);
        	
			request.setAttribute("message", message + "Config: "
			+config + "Time: " + Double.toString((double) elapsedTime/1000000));
			request.getRequestDispatcher("Statement.jsp").forward(request,response);
			

        }
        catch (SQLException ex) {
        	System.out.println(ex.getMessage());
        } 
        catch(java.lang.Exception ex) {

        }
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
  
}
