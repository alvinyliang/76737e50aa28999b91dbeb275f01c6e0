package Fabflix;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import javax.json.*;
import java.sql.*;

public class StarServlet extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=utf-8");
        PreparedStatement stmt;
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        
//        HttpSession session = request.getSession(false);  
//    	try {
//	        if (session.getAttribute("authenticated") == null) {  
//	        	response.sendRedirect("../login.jsp");
//	    		return;
//	        }  
//    	} catch (Exception e) {
//    		
//    	}

        try{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	
        	// ./Fabflix/Star?sid=0000
            String starID = request.getParameter("sid");
            
            
            stmt = conn.prepareStatement("select * from stars where id=" + starID + " ;");
         
        	ResultSet rs = stmt.executeQuery();
        	JsonObjectBuilder builder = Json.createObjectBuilder();
			
	        while (rs.next()){
	        	
	        	builder.add("id", rs.getString("id"));
	        	builder.add("name" ,rs.getString("first_name") + 
	        				  " " + rs.getString("last_name"));
	        	builder.add("dob", rs.getString("dob"));
	        	
	        	String photo 	= rs.getString("photo_url");
	        	
	        	
	        	
	        	String no_profile = "http://www.solarimpulse.com/img/profile-no-photo.png";
	        	
	        	try{
		            URL url = new URL(photo);
		            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		            connection.setRequestMethod("HEAD");
		            connection.setConnectTimeout(10);
		            
		            connection.connect();
		            int code = connection.getResponseCode();
		            if (code != HttpURLConnection.HTTP_OK){
		            	photo = no_profile;
		            }
	        	}
	        	catch (Exception e){
	        		photo = no_profile;
	        	}
	    		
				builder.add("photo", photo);		
				
	        } 
	        
	        
	        // build JsonArray for featured movies
	        stmt = conn.prepareStatement(""
	        		+ "select movies.title as name, movies.id "
	        		+ "from movies join stars_in_movies "
	        		+ "where (stars_in_movies.star_id = " + starID +" "
	        		+ "and stars_in_movies.movie_id = movies.id);");
	        
	        builder.add("movies", buildListJson(stmt));
	        
	        
	        
	        	
	        
	        out.print(builder.build());
	       
	        
	        
            
	       
        	
            
            
        	
        }
        catch (SQLException ex) {
            while (ex != null) {
                  System.out.println ("SQL Exception:  " + ex.getMessage ());
                  ex = ex.getNextException ();
              }  // end while
          }  // end catch SQLException
        catch(java.lang.Exception ex)
          {
              out.println("<HTML>" +
                          "<HEAD><TITLE>" +
                          "MovieDB: Error" +
                          "</TITLE></HEAD>\n<BODY>" +
                          "<P>SQL error in doGet: " +
                          ex.getMessage() + "</P></BODY></HTML>");
              
              return;
          }
       out.close();
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

    private JsonArray buildListJson(PreparedStatement stmt) throws SQLException{
    	// return an JsonArray of object include (name, id)
    	ResultSet rs = stmt.executeQuery();
    	JsonArrayBuilder resultBuilder = Json.createArrayBuilder();
    	while(rs.next()){
    		JsonObjectBuilder elementBuilder = Json.createObjectBuilder();
    		elementBuilder.add("name", rs.getString("name"));
    		elementBuilder.add("id", rs.getString("id"));
    		resultBuilder.add(elementBuilder);
    		
    	}
    	
    	return resultBuilder.build();
    }
    
    
 
}
