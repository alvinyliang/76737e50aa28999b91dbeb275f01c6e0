package Fabflix;

import javax.servlet.http.HttpServlet;
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
import java.util.ArrayList;

public class BrowseGenreServlet extends HttpServlet {
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
        
        String genre = request.getParameter("genre");
        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        
        ArrayList<Movie> movieList;
    	movieList = queryMovies(genre, page, sort, order);
    	
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=utf-8");
        out.print(buildMovieListJson(movieList));
    	
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
    	
    	String genre = request.getParameter("genre");
        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        
        if (genre == null) {
        	request.getRequestDispatcher("/WEB-INF/Genres.jsp").forward(request,response);
            return;
            
        }
        
        if (page == null || sort == null || order == null) {
            ArrayList<Movie> movieList;
        	movieList = queryMovies(genre, "1", "title", "desc");
        	
        	session.setAttribute("movies", movieList);
            session.setAttribute("lastGenre", genre);
            session.setAttribute("lastOrder", order);
            session.setAttribute("lastSort", sort);
    		
            request.getRequestDispatcher("/WEB-INF/Genres.jsp").forward(request,response);
            return;
        }
        
        //Check paramaters
        if ((sort.equals("title") || sort.equals("year")) == false || (order.equals("desc") || order.equals("asc")) == false) {
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
    	movieList = queryMovies(genre, page, sort, order);
    	
    	session.setAttribute("movies", movieList);
        session.setAttribute("lastGenre", genre);
        session.setAttribute("lastOrder", order);
        session.setAttribute("lastSort", sort);
		
        request.getRequestDispatcher("/WEB-INF/Genres.jsp").forward(request,response);
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

	        	starList.add(star);
	        }
        conn.close();
        } catch (Exception e) {
        	
        }
    	return starList;
    }
    
    private ArrayList<Movie> queryMovies(String genre, String page, String sort, String order) {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);       
            stmt = conn.prepareStatement("SELECT * FROM genres_in_movies join movies join genres on genres_in_movies.movie_id = movies.id and genres_in_movies.genre_id = genres.id WHERE name = ? order by " + sort + " " + order);
            stmt.setString(1, genre);
            		//+ "limit "+ numMovie +" offset " + (numMovie * (pageNum-1)) +" ;");     // pagination
            
    	    ResultSet rs = stmt.executeQuery();
        	DatabaseQueries dbQ = new DatabaseQueries(conn);

	        while (rs.next()){
	        	Movie movie = new Movie();
	        	movie.id = rs.getInt(2);
	        	movie.title = rs.getString(4);
	        	movie.year = Integer.parseInt(rs.getString(5));
	        	movie.director = rs.getString(6);
	        	String banner = rs.getString(7);
	        	movie.banner = banner;
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
	        int y = 0;
        } catch (Exception e) {
        	
        }
        
    	return movieList;
    }
    


}
