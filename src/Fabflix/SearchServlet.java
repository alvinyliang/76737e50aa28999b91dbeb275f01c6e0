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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SearchServlet extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    int queryLimit = 10;    
    static final String DEBUG = "OFF";
    long elapsedTime = 0;
    int numQ = 0;
    
    
    private JsonObject buildMovieListJson(ArrayList<Movie> movieList, int totalPages, int pageId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        //iterate over movie list
        for (Movie movie : movieList) {
        	JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        	
        	objectBuilder.add("id", movie.id);
        	objectBuilder.add("title", movie.title);
        	objectBuilder.add("year", movie.year);
        	objectBuilder.add("director", movie.director);
        	objectBuilder.add("banner", movie.banner);
        	
        	//paging
        	objectBuilder.add("pageId", pageId);
            objectBuilder.add("numPages", totalPages);
            
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
    

    
    //star first name, star last name
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	

    	
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        HttpSession session = request.getSession(true);
        
        try{
        	long startTime = System.nanoTime();
        	long endTime = System.nanoTime();
    		
        	startTime = System.nanoTime();
        	/////////////////////////////////
    		/// ** part to be measured ** ///
        	
            InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
            DBConnection dbConn = new DBConnection(input);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
            
        	DatabaseQueries dbQ = new DatabaseQueries(conn);
            
    		/// ** part to be measured ** ///
    		/////////////////////////////////
        	endTime = System.nanoTime();
        	elapsedTime += endTime - startTime;

        	
	        //get params	
			String title = request.getParameter("title");
			String director = request.getParameter("director");
			String year = request.getParameter("year");
			String star = request.getParameter("star");
	        String sort = request.getParameter("sort");
	        String order = request.getParameter("order");
	        String limit = request.getParameter("limit");		
        	String auto_complete_text = request.getParameter("auto_complete");

	        
	        queryLimit = Integer.parseInt(limit);
			 
			int pageId = getPageId(request);
			
        	
			startTime = System.nanoTime();
        	/////////////////////////////////
    		/// ** part to be measured ** ///
			
			
			//execute table results and count query
			int countResults = querySearchParamCount(conn, title, director, star, year);

    		/// ** part to be measured ** ///
    		/////////////////////////////////
        	endTime = System.nanoTime();
        	elapsedTime += endTime - startTime;
        	
        	
			
			int totalPages = (int) Math.ceil( (double) countResults / (double) queryLimit);
			
			session.setAttribute("countResults", countResults);
            session.setAttribute("pageId", pageId);
            session.setAttribute("numPages", totalPages);
            session.setAttribute("movie_title", title);
            session.setAttribute("year", year);
            session.setAttribute("director", director);
            session.setAttribute("star", star);
            session.setAttribute("auto_complete", auto_complete_text);

            startTime = System.nanoTime();
        	/////////////////////////////////
    		/// ** part to be measured ** ///
			

        	ArrayList<Movie> movieList = querySearchParam(dbQ, conn, title, director, star, year, pageId, sort, order);	
        	
        	/// ** part to be measured ** ///
    		/////////////////////////////////
        	endTime = System.nanoTime();
        	elapsedTime += endTime - startTime;
        	
        	
        	System.out.println("Requet received");
        	
        	String[] auto_complete_parts = auto_complete_text.split(" ");
        	
        	
    		
        	if (auto_complete_text != null && !auto_complete_text.trim().isEmpty()){
            	System.out.println("I got in");

	        	
	        	
	        	//TODO: full text matching
	        	//	SELECT movieId FROM movies WHERE MATCH (title) AGAINST ('+graduate -michigan' IN BOOLEAN MODE)
	        	String match_against = "";
	        	
	        	for (int i = 0; i < auto_complete_parts.length; i++){
	        		//check last word
	        		if (i == (auto_complete_parts.length -1)){
	            		match_against += "+" + auto_complete_parts[i] + "*";
	        		}
	        		else{
	            		match_against += "+" + auto_complete_parts[i] + " ";
	        		}
	        	}
	        	
	        	
	    		
	        	startTime = System.nanoTime();
	        	/////////////////////////////////
	    		/// ** part to be measured ** ///
				
	        	
	        	String matchQuery = "SELECT * FROM movies WHERE MATCH (title) AGAINST ('" + match_against + "' IN BOOLEAN MODE)";
	        	System.out.println(matchQuery);
	        	PreparedStatement stmt = conn.prepareStatement(matchQuery);
	        	ResultSet rs = stmt.executeQuery();
	        	movieList = getSearchParamResults(rs, dbQ);
	        	
	        	/// ** part to be measured ** ///
	    		/////////////////////////////////
	        	endTime = System.nanoTime();
	        	elapsedTime += endTime - startTime;
	        	numQ++;
	        	
        	}

    		
        	response.setContentType("application/json;charset=utf-8");
        	String rootPath = getServletContext().getRealPath("/");
			
        	PerformanceLogger.log(rootPath + "/logs/TestLog.log", 
    				elapsedTime,numQ, "TJ", request.getParameter("config"), auto_complete_text, title);
        	
        	elapsedTime = 0;
        	numQ = 0;
	        out.print(buildMovieListJson(movieList, totalPages, pageId));
	        out.flush();
	        return;
        } catch (SQLException ex) {

        } catch(java.lang.Exception ex){

        } finally{
            out.close();
            try {conn.close();} catch (SQLException e) {}
        }
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
    
    protected boolean validURL(String url){
    	try{
            URL valid_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)valid_url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(20);
            connection.connect();
            int code = connection.getResponseCode();

            if (code != HttpURLConnection.HTTP_OK){
        		return false;
            }
            
            return true;
    	}
    	catch (Exception e){
    		return false;
    	}
    	
    }

    protected int getPageId(HttpServletRequest request){
		int pageId;		
				
		if (request.getParameter("pageId") != null){
			pageId = Integer.parseInt(request.getParameter("pageId"));
		}
		else{
			pageId = 1;
		}

    	return pageId;
    }
    
    //conn, title, director, star, year
    protected int querySearchParamCount(Connection conn, String title, String director, String star, String year) throws SQLException{

    	PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM movies WHERE "
    			+ "(title LIKE ? AND year LIKE ? AND director LIKE ? AND "
    			+ "id IN "
    			+ "(SELECT movie_id FROM stars_in_movies WHERE star_id IN "
    			+ "(SELECT id FROM stars WHERE concat(first_name, ' ', last_name) "
    					+ "LIKE ?))) ");
    	
    	//ensure all fields not empty
    	if ((title == null || title.trim().isEmpty()) && 
    			(director == null || director.trim().isEmpty()) && 
    			(year == null || year.trim().isEmpty()) &&
    			(star == null || star.trim().isEmpty())
    			){
        	stmt.setString(1, "");
        	stmt.setString(2, "");	
        	stmt.setString(3, "");
        	stmt.setString(4, "");
        }
    	else{
            stmt.setString(1, '%' + title + '%');
        	stmt.setString(2, '%' + year + '%');
        	stmt.setString(3, '%' + director + '%');
        	stmt.setString(4, star + '%');
    	}
    	
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int itemCount = rs.getInt(1);
        rs.close();
        numQ++;
        return itemCount;
		
    }
    
    //conn, title, director, year, star, pageId
    protected ArrayList<Movie> querySearchParam(
    		DatabaseQueries dbQ, Connection conn, String title, String director, 
    		String star, String year, int pageId, String sort, String order) throws SQLException{

    	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE "
    			+ "(title LIKE ? AND year LIKE ? AND director LIKE ? AND "
    			+ "id IN "
    			+ "(SELECT movie_id FROM stars_in_movies WHERE star_id IN "
    			+ "(SELECT id FROM stars WHERE concat(first_name, ' ', last_name) "
    					+ "LIKE ?))) "
    			+ "ORDER BY movies." + sort + " " + order + " LIMIT ? OFFSET ? ");
    	//AND director LIKE ? AND year LIKE ?

    	
        //fuzzy match title

    	//ensure all fields not empty
    	if ((title == null || title.trim().isEmpty()) && 
    			(director == null || director.trim().isEmpty()) && 
    			(year == null || year.trim().isEmpty()) &&
    			(star == null || star.trim().isEmpty())
    			){
        	stmt.setString(1, "");
        	stmt.setString(2, "");	
        	stmt.setString(3, "");
        	stmt.setString(4, "");
        }
    	else{
            stmt.setString(1, '%' + title + '%');
        	stmt.setString(2, '%' + year + '%');
        	stmt.setString(3, '%' + director + '%');
        	stmt.setString(4, '%' + star + '%');

    	}

        
        //set number of results to return
    	stmt.setInt(5, queryLimit); //request.getParameter("numberResultsPerPage");
		
    	//set offest # using pageId
		if (pageId == 1){
	        stmt.setInt(6, 0);
		}
		else{
        	stmt.setInt(6, (pageId-1)*queryLimit); //offset results by pageId
		}
		
    	ResultSet rs = stmt.executeQuery();
    	numQ++;
    	return getSearchParamResults(rs, dbQ);
    }
    
    protected ArrayList<Movie> getSearchParamResults(ResultSet rs, DatabaseQueries dbQ) throws SQLException{
    	ArrayList<Movie> movieList = new ArrayList<Movie>();
    	String no_profile = "https://i.imgur.com/OZISao4.png";

    	while (rs.next()){
    		
        	Movie movie = new Movie();
        	
        	movie.id = Integer.parseInt(rs.getString(1));
        	movie.title = rs.getString(2);
        	movie.year = Integer.parseInt(rs.getString(3));
        	movie.director = rs.getString(4);
        	
        	String banner = rs.getString(5);
        	movie.stars = dbQ.queryStars(movie.id);
        	numQ++;
        	movie.genres = dbQ.queryGenres(movie.id);
        	numQ++;
        	//check for valid banner link
//        	if (!validURL(banner)){
//        		banner = no_profile;
//        	}
        	
        	movie.banner = banner;
        	
        	movieList.add(movie);				
        } 
	    	
    	rs.close();
    	
    	return movieList;
    	
    }	


}