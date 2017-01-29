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
        	
        	//Fabflix/Browse/Genre?genreName=Family
            String genreName = request.getParameter("genreName");
            
            
            
	        if (genreName.equalsIgnoreCase("SciFi")){
	        	genreName = "Sci%Fi";
	        }
            genreName = "%" + genreName + "%";
            
            out.println(genreName);
	        out.println("<br>");
            
            stmt = conn.prepareStatement("select * from movies "
            		+ "where movies.id in( "
            		+ "select genres_in_movies.movie_id "
            		+ "from genres_in_movies join genres on (genres.id = genres_in_movies.genre_id) "
            		+ "where genres.name like \"" + genreName + "\") "
            		+ "order by movies.title " 					// change order
            		+ "limit 10 offset " + (10 * 0) +" ;");     // pagination
            
        	ResultSet rs = stmt.executeQuery();
			
			out.println("<div class='card-columns'>");
	        while (rs.next()){
	        	String title = rs.getString(2);
	        	String director = rs.getString(4);
	        	String banner = rs.getString(5);
	        	
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
