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

import java.sql.*;

public class BrowseGenreServlet extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        PreparedStatement stmt;
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        HttpSession session = request.getSession(true);
        


        try{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	
        	//Fabflix/Browse/Genre?gid=00000
            String genreID = request.getParameter("gid");
            
            String order = request.getParameter("order");  
            	// ta: order by title asc	td: order by title desc
            	// ya: order by year asc	yd: order by year desc
            int pageNum = Integer.parseInt(request.getParameter("p"));
            int numMovie = Integer.parseInt(request.getParameter("m"));
            
            
	        out.println("<br>");
	        
	        String orderBy = ""; 
	        if (order.contains("y")){
	        	orderBy += "year ";
	        }else{
	        	orderBy += "title ";
	        }
	        
	        if (order.contains("d")){
	        	orderBy += "desc";
	        }else{
	        	orderBy += "asc";
	        }
            
	        System.out.println( "GenreID: " + genreID + "; "+
					"orderBy:   " + orderBy + "; "+
					"PageNum:   " + pageNum + "; " +
 				   	"numMovie:  " + numMovie);
	        
            stmt = conn.prepareStatement("select * "
            		+ "from genres_in_movies join movies "
            		+ "on genres_in_movies.movie_id = movies.id "
            		+ "where genres_in_movies.genre_id = " + genreID + " "
            		+ "order by movies."+ orderBy +" " 					// change order
            		+ "limit "+ numMovie +" offset " + (numMovie * (pageNum-1)) +" ;");     // pagination
            
            // System.out.println(stmt);
        	ResultSet rs = stmt.executeQuery();
			
			out.println("<div class='card-columns'>");
	        while (rs.next()){
	        	
	        	String title = rs.getString("title");
	        	String director = rs.getString("director");
	        	String banner = rs.getString("banner_url");
	        	
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
	    		


	        	out.format(""
	        			+ "<div class='card'> "
	        			+ "	<img class='card-img-top' src='%s' alt='Profile not found.'> "
	        			+ "		<div class='card-block'> "
	        			+ "			<h4 class='card-title'>%s</h4> "
	        			+ "			<p class='card-text'>%s</p> "
	        			+ "		</div>"
	        			+ "</div>"
	        			, banner, title, director);
	        } 
	        out.println("</div>");
            
	       
	        
	        	
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

    


}
