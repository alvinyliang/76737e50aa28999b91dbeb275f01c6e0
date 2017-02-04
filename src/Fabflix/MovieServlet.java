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

public class MovieServlet extends HttpServlet {
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
        	
        	// ./Fabflix/Movie?mid=0000
            String movieID = request.getParameter("mid");
            
            
	        
            stmt = conn.prepareStatement("select * from movies where id=" + movieID + " ;");
         
        	ResultSet rs = stmt.executeQuery();
        	JsonObjectBuilder builder = Json.createObjectBuilder();
			
	        while (rs.next()){
	        	
	        	
	        	String banner 	= rs.getString("banner_url");
	        	String trailer 	= rs.getString("trailer_url");
	        	
	        	builder.add("id", rs.getString("id"));
	        	builder.add("title", rs.getString("title"));
	        	builder.add("year", rs.getString("year"));
	        	builder.add("director", rs.getString("director"));
	        	
	        	
	        	
	        	String no_profile = "http://www.solarimpulse.com/img/profile-no-photo.png";
	        	
	        	try{
		            URL url = new URL(banner);
		            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		            connection.setRequestMethod("HEAD");
		            connection.setConnectTimeout(10);
		            
		            connection.connect();
		            int code = connection.getResponseCode();
		            if (code != HttpURLConnection.HTTP_OK){
		        		banner = no_profile;
		            }
	        	}
	        	catch (Exception e){
	        		banner = no_profile;
	        	}
	    		
						
				try{
			            URL url = new URL(trailer);
			            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			            connection.setRequestMethod("HEAD");
			            connection.setConnectTimeout(10);
			            
			            connection.connect();
			            int code = connection.getResponseCode();
			            if (code != HttpURLConnection.HTTP_OK){
			            	trailer = "#";
			            }
		        	}catch (Exception e){
		        		trailer = "#";
		        	}
						

		        builder.add("banner",banner);
	        	builder.add("trailer", trailer);
	        } 
	        
	        
	        // build JsonArray for stars
	        stmt = conn.prepareStatement(""
	        		+ "select CONCAT(stars.first_name ,\" \", stars.last_name) as name, stars.id "
	        		+ "from stars join stars_in_movies "
	        		+ "where (stars_in_movies.movie_id = " + movieID +" "
	        		+ "and stars.id = stars_in_movies.star_id);");
	        
	        builder.add("stars", buildListJson(stmt));
	        
	        // build JsonArray for genres
	        stmt = conn.prepareStatement("select genres.name, genres.id "
	        		+ "from genres join genres_in_movies "
	        		+ "where (genres_in_movies.movie_id = " + movieID + " "
	        		+ "and genres.id = genres_in_movies.genre_id);");
	        
	        builder.add("genres", buildListJson(stmt));
	        
	        
	        	
	        
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