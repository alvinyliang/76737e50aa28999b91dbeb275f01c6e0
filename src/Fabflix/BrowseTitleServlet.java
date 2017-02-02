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

public class BrowseTitleServlet extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);  
    	try {
	        if (session.getAttribute("authenticated") == null) {  
	        	response.sendRedirect("../login.jsp");
	    		return;
	        }  
    	} catch (Exception e) {
    		
    	}
    	
        String titleChar = request.getParameter("title");
        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        
        ArrayList<Movie> movieList;
    	movieList = queryMovies(titleChar, page, sort, order);
    	
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=utf-8");
        out.print(buildMovieListJson(movieList));
        return;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
    
    private JsonObject buildMovieListJson(ArrayList<Movie> movieList) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Movie movie : movieList) {
        	JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        	objectBuilder.add("title", movie.title);
        	objectBuilder.add("year", movie.year);
        	objectBuilder.add("director", movie.director);
        	objectBuilder.add("banner", movie.banner);
        	arrayBuilder.add(objectBuilder);
        }
        builder.add("movies", arrayBuilder);
        JsonObject jsonMovieList = builder.build();
        return jsonMovieList;
    }

    private ArrayList<Movie> queryMovies(String titleChar, String page, String sort, String order) {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);       
    	    stmt = conn.prepareStatement("select * from movies "
    	    		+ "where substring(movies.title from 1 for 1) "
    	    		+ "= \""+ titleChar +"\" "
    	    		+ "order by movies."+ sort + " " + order +" ");		//change order 
    	    		//+ "limit 10 offset " + ( numMovie * (pageNum-1)) +" ;");	//pagination
    
    	    ResultSet rs = stmt.executeQuery();
	
	        while (rs.next()){
	        	Movie movie = new Movie();
	        	movie.title = rs.getString(2);
	        	movie.year = Integer.parseInt(rs.getString(3));
	        	movie.director = rs.getString(4);
	        	String banner = rs.getString(5);
	        	movie.banner = banner;
	        	String no_profile = "https://i.imgur.com/OZISao4.png";

	        	try{
		            URL url = new URL(banner);
		            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		            connection.setRequestMethod("HEAD");
		            connection.setConnectTimeout(10);
		            connection.connect();
		            int code = connection.getResponseCode();
		            if (code != HttpURLConnection.HTTP_OK){
		            	movie.banner = no_profile;
		            }
	        	}
	        	catch (Exception e) {
	        		movie.banner = no_profile;
	        	}
	        	movieList.add(movie);
	    		
	        }
    	
        } catch (Exception e) {
        	
        }
    	return movieList;
    }

}
