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

import java.sql.*;

public class BrowseMenuServlet extends HttpServlet {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PreparedStatement stmt;
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        
        try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	
        	//New statement ignores movies that are in the genre list but no movies for it exist
        	stmt = conn.prepareStatement("SELECT distinct name from genres join genres_in_movies on genres.id = genres_in_movies.genre_id order by genres.name;");
	        //stmt = conn.prepareStatement("select distinct name from genres order by genres.name;");
	        
			ResultSet rs = stmt.executeQuery();
	        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	        
	        while (rs.next()){
	        	String genre = rs.getString("name");
	        	arrayBuilder.add(Json.createObjectBuilder().add("name", genre));
	        }
	        
	        JsonObjectBuilder builder = Json.createObjectBuilder();
	        builder.add("genres", arrayBuilder);
	        JsonObject genreList = builder.build();
	        
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json;charset=utf-8");
	        out.print(genreList);
        }
        catch (SQLException ex) {

        } 
        catch(java.lang.Exception ex) {

        }
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
  
}
