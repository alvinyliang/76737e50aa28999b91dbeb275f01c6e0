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
import java.util.ArrayList;
import java.util.Hashtable;

public class SearchServlet extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    int queryLimit = 10;    
    static final String DEBUG = "OFF";
    
    
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
        
        HttpSession session = request.getSession(true);
        Connection conn = null;
        
        
        try{
            InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
            DBConnection dbConn = new DBConnection(input);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
            
        	DatabaseQueries dbQ = new DatabaseQueries(conn);
            


	        //get params	
			String title = request.getParameter("title");
			String director = request.getParameter("director");
			String year = request.getParameter("year");
			String star = request.getParameter("star");
	        String sort = request.getParameter("sort");
	        String order = request.getParameter("order");
	        String limit = request.getParameter("limit");			
	        queryLimit = Integer.parseInt(limit);
			 
			int pageId = getPageId(request);
			
        	//execute table results and count query
			int countResults = querySearchParamCount(conn, title, director, star, year);
			int totalPages = (int) Math.ceil( (double) countResults / (double) queryLimit);
			
			session.setAttribute("countResults", countResults);
            session.setAttribute("pageId", pageId);
            session.setAttribute("numPages", totalPages);
            session.setAttribute("movie_title", title);
            session.setAttribute("year", year);
            session.setAttribute("director", director);
            session.setAttribute("star", star);
			

        	ArrayList<Movie> movieList = querySearchParam(dbQ, conn, title, director, star, year, pageId, sort, order);
        	
        	response.setContentType("application/json;charset=utf-8");
	        out.print(buildMovieListJson(movieList, totalPages, pageId));
	        out.flush();
        } catch (SQLException ex) {
            while (ex != null) {
                  System.out.println ("SQL Exception:  " + ex.getMessage ());
                  ex = ex.getNextException ();
              }  // end while
        } catch(java.lang.Exception ex){
              out.println("<HTML>" +
                          "<HEAD><TITLE>" +
                          "MovieDB: Error" +
                          "</TITLE></HEAD>\n<BODY>" +
                          "<P>SQL error in doGet: " +
                          ex.getMessage() + "</P></BODY></HTML>");
              return;
        } finally{
            out.close();
            try {conn.close();} catch (SQLException e) {}
        }
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    			+ "(SELECT id FROM moviedb.stars WHERE concat(first_name, ' ', last_name) "
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
    			+ "(SELECT id FROM moviedb.stars WHERE concat(first_name, ' ', last_name) "
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
    	System.out.printf("title: %s, director: %s, year: %s, star: %s \n", title, director, year, star);

        
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
        	movie.genres = dbQ.queryGenres(movie.id);
        	
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