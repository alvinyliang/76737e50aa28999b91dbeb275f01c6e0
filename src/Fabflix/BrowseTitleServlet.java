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
        
        if (titleChar == null || page == null || sort == null || order == null) {
        	request.getRequestDispatcher("/WEB-INF/Titles.jsp").forward(request,response);
        	return;
        }
        
        //Check paramaters
        if (titleChar.length() != 1 || (sort.equals("title") || sort.equals("year")) == false || (order.equals("desc") || order.equals("asc")) == false) {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    	String lastOrder = (String) session.getAttribute("lastOrder");
    	String lastSort = (String) session.getAttribute("lastSort");
    	
    	if (lastOrder != null && lastSort != null) {
    		if (sort.equals(lastSort)) {
    			order = lastOrder.equals("desc") ? "asc" : "desc";
    		}
    	}
    	
        ArrayList<Movie> movieList;
    	movieList = queryMovies(titleChar, page, sort, order);
    	
    	session.setAttribute("movies", movieList);
        session.setAttribute("lastClick", titleChar);
        session.setAttribute("lastOrder", order);
        session.setAttribute("lastSort", sort);
		
        request.getRequestDispatcher("/WEB-INF/Titles.jsp").forward(request,response);
    }
    
    private JsonObject buildMovieListJson(ArrayList<Movie> movieList) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Movie movie : movieList) {
        	JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        	objectBuilder.add("id", movie.id);
        	objectBuilder.add("title", movie.title);
        	objectBuilder.add("year", movie.year);
        	objectBuilder.add("director", movie.director);
        	objectBuilder.add("banner", movie.banner);
        	
        	//iterate over stars list
            JsonArrayBuilder starArrayBuilder = Json.createArrayBuilder();
            for (Star star : movie.stars) {
            	JsonObjectBuilder starObjectBuilder = Json.createObjectBuilder();
            	starObjectBuilder.add("id", star.id);
            	starObjectBuilder.add("firstName", star.firstName);
            	starObjectBuilder.add("lastName", star.lastName);
            	starArrayBuilder.add(starObjectBuilder);

            }
        	objectBuilder.add("stars", starArrayBuilder);
        	
            //iterate over genres list
            JsonArrayBuilder genresArrayBuilder = Json.createArrayBuilder();
            for (String genreName : movie.genres.values()) {
            	JsonObjectBuilder genresObjectBuilder = Json.createObjectBuilder();
            	genresObjectBuilder.add("genreName", genreName);
            	genresArrayBuilder.add(genresObjectBuilder);

            }
        	objectBuilder.add("genres", genresArrayBuilder);        	
        	
        	arrayBuilder.add(objectBuilder);
        }
        builder.add("movies", arrayBuilder);
        JsonObject jsonMovieList = builder.build();
        return jsonMovieList;
    }
    
    private ArrayList<Star> queryStars(int movieId) {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        ArrayList<Star> starList = new ArrayList<Star>();
        try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);       
    	    stmt = conn.prepareStatement("SELECT * FROM stars_in_movies join stars ON stars_in_movies.star_id = stars.id WHERE movie_id = ?");
    	    stmt.setInt(1, movieId);
    
    	    ResultSet rs = stmt.executeQuery();
	
	        while (rs.next()){
	        	Star star = new Star();
	        	star.id = rs.getInt(1);
	        	star.firstName = rs.getString(4);
	        	star.lastName = rs.getString(5);
	        	star.dob = rs.getString(6);
	        	star.photo = rs.getString(7);

//	        	try {
//		        	URL url = new URL(star.photo);
//		        	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//		        	huc.setRequestMethod("HEAD");
//		        	int responseCode = huc.getResponseCode();
//	
//		        	if (responseCode != 200) {
//		        		star.photo = "http://i.imgur.com/maDRWrD.png";
//		        	}
//	        	} catch (Exception e) {
//	        		
//	        	}
	        	starList.add(star);
	        }
        conn.close();
        } catch (Exception e) {
        	
        }
    	return starList;
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
        	DatabaseQueries dbQ = new DatabaseQueries(conn);

	        while (rs.next()){
	        	Movie movie = new Movie();
	        	movie.id = rs.getInt(1);
	        	movie.title = rs.getString(2);
	        	movie.year = Integer.parseInt(rs.getString(3));
	        	movie.director = rs.getString(4);
	        	String banner = rs.getString(5);
	        	movie.banner = banner;
	        	String no_profile = "https://i.imgur.com/OZISao4.png";
	        	movie.stars = queryStars(movie.id);
	        	movie.genres = dbQ.queryGenres(movie.id);

//	        	try {
//		        	URL url = new URL(banner);
//		        	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//		        	huc.setRequestMethod("HEAD");
//		        	int responseCode = huc.getResponseCode();
//	
//		        	if (responseCode != 200) {
//		        		movie.banner = "https://i.imgur.com/OZISao4.png";
//		        	}
//	        	} catch (Exception e) {
//	        		
//	        	}
	        	movieList.add(movie);
	        }
        conn.close();
        } catch (Exception e) {
        	
        }
    	return movieList;
    }

}
