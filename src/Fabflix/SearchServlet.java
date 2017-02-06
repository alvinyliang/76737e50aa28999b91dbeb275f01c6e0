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
    
    
    private JsonObject buildMovieListJson(ArrayList<Movie> movieList, int totalPages) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Movie movie : movieList) {
        	JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        	
        	objectBuilder.add("movie_id", movie.id);
        	objectBuilder.add("title", movie.title);
        	objectBuilder.add("year", movie.year);
        	objectBuilder.add("director", movie.director);
        	objectBuilder.add("banner", movie.banner);
        	
        	
            objectBuilder.add("numPages", totalPages);

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
        
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        HttpSession session = request.getSession(true);
        Connection conn = null;
        
        
        try{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        

	        //get params	
			String title = request.getParameter("title");
			String director = request.getParameter("director");
			String year = request.getParameter("year");
			String star = request.getParameter("star");
			
			 
			int pageId = getPageId(request);
			
        	//execute table results and count query
			int countResults = querySearchParamCount(conn, title, director, star, year);
			int totalPages = (int) Math.ceil( (double) countResults / (double) queryLimit);

			setPrevPagination(session, pageId);
			setNextPagination(session, pageId, totalPages);

			
			
			session.setAttribute("countResults", countResults);
            session.setAttribute("pageId", pageId);
            session.setAttribute("numPages", totalPages);
            session.setAttribute("movie_title", title);
            session.setAttribute("year", year);
            session.setAttribute("director", director);
            session.setAttribute("star", star);
			

        	ArrayList<Movie> movieList = querySearchParam(conn, title, director, star, year, pageId);
        	
        	response.setContentType("application/json;charset=utf-8");
	        out.print(buildMovieListJson(movieList, totalPages));
	        out.flush();
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
        finally{
            out.close();
            try {
				conn.close();
			} catch (SQLException e) {
			}
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

    protected ArrayList<Movie> getSearchParamResults(ResultSet rs) throws SQLException{
    	ArrayList<Movie> movieList = new ArrayList<Movie>();
    	String no_profile = "https://i.imgur.com/OZISao4.png";

    	while (rs.next()){
    		
        	Movie movie = new Movie();
        	
        	movie.id = Integer.parseInt(rs.getString(1));
        	movie.title = rs.getString(2);
        	movie.year = Integer.parseInt(rs.getString(3));
        	movie.director = rs.getString(4);
        	
        	String banner = rs.getString(5);
        	
        	//check for valid banner link
        	if (!validURL(banner)){
        		banner = no_profile;
        	}
        	
        	movie.banner = banner;
        	
        	movieList.add(movie);				
        } 
	    	
    	rs.close();
    	
    	return movieList;
    	
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
    
    protected void setPrevPagination(HttpSession session, int pageId){
    	if (pageId - 1 > 0){
			session.setAttribute("prev", pageId-1);
		}
		else{
			session.setAttribute("prev", 1);
		}    	
    }

    
    protected void setNextPagination(HttpSession session, int pageId, int totalPages){
		if (pageId + 1 <= totalPages){
			session.setAttribute("next", pageId+1);
		}
		else{
			session.setAttribute("next", totalPages+1);
		}
    	
    }

    //conn, title, director, star, year
    protected int querySearchParamCount(Connection conn, String title, String director, String star, String year) throws SQLException{

    	PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM movies WHERE (title LIKE ? AND year LIKE ? AND director LIKE ?)");
    	
    	//ensure all fields not empty
    	if ((title == null || title.isEmpty()) && (director == null || director.isEmpty()) && (year == null || year.isEmpty())){
        	stmt.setString(1, "");
        	stmt.setString(2, "");	
        	stmt.setString(3, "");
        }
    	else{
            stmt.setString(1, '%' + title + '%');
        	stmt.setString(2, '%' + year + '%');
        	stmt.setString(3, '%' + director + '%');
    	}
		
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int itemCount = rs.getInt(1);
        rs.close();
        
        return itemCount;
		
    }
    
    //conn, title, director, year, star, pageId
    protected ArrayList<Movie> querySearchParam(Connection conn, String title, String director, String star, String year, int pageId) throws SQLException{

    	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE (title LIKE ? AND year LIKE ? AND director LIKE ?) ORDER BY title ASC LIMIT ? OFFSET ? ");
    	//AND director LIKE ? AND year LIKE ?

        //fuzzy match title

    	//ensure all fields not empty
    	if ((title == null || title.isEmpty()) && 
    			(director == null || director.isEmpty()) && 
    			(year == null || year.isEmpty())
    			){
        	stmt.setString(1, "");
        	stmt.setString(2, "");	
        	stmt.setString(3, "");
        }
    	else{
            stmt.setString(1, '%' + title + '%');
        	stmt.setString(2, '%' + year + '%');
        	stmt.setString(3, '%' + director + '%');
    	}
    	System.out.printf("title: %s, director: %s, year: %s, star: %s \n", title, director, year, star);

        
        //set number of results to return
    	stmt.setInt(4, queryLimit); //request.getParameter("numberResultsPerPage");
		
    	//set offest # using pageId
		if (pageId == 1){
	        stmt.setInt(5, 0);
		}
		else{
        	stmt.setInt(5, (pageId-1)*queryLimit); //offset results by pageId
		}
		
    	ResultSet rs = stmt.executeQuery();
    	return getSearchParamResults(rs);
    }
    
	


}