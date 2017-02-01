package Fabflix;

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
import java.util.Hashtable;

public class SearchServlet extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    protected boolean validURL(String url){
    	try{
            URL valid_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)valid_url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(10);
            connection.connect();
            
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
        		return true;
            }
            
            return false;
    	}
    	catch (Exception e){
    		return false;
    	}
    	
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        PreparedStatement stmt;
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        HttpSession session = request.getSession(true);
        


        try{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	stmt = conn.prepareStatement("SELECT * FROM movies WHERE title LIKE ? LIMIT 20");
        	
            String movie_title = request.getParameter("movie_title");
	        out.println(movie_title);
	        out.println("<br>");
	        String pageId = request.getParameter("pageId");
	        
	        
	        
	        if (movie_title == null || movie_title.isEmpty()){
	        	stmt.setString(1, movie_title);	
	        }
	        else{
	        	stmt.setString(1, '%' + movie_title + '%');
	        }
	        
	        
			ResultSet rs = stmt.executeQuery();
			Hashtable<Integer, Hashtable<String, String>> sql_results = new Hashtable<Integer, Hashtable<String, String>>();
			
			//key for hashtable
			int countID = 0;
			while (rs.next()){
				
				Hashtable<String, String> to_return = new Hashtable<String, String>();
				
	        	String title = rs.getString(2);
	        	String director = rs.getString(4);
	        	String banner = rs.getString(5);
	        	
	        	String no_profile = "http://www.solarimpulse.com/img/profile-no-photo.png";

	        	//check for valid banner link
	        	if (!validURL(banner)){
	        		banner = no_profile;
	        	}
	    		
	        	to_return.put("title", title);
	        	to_return.put("director", director);
	        	to_return.put("banner", banner);
	        	
				sql_results.put(countID, to_return);
				
				countID++;
	        } 
	        
			session.setAttribute("results", sql_results);
	        session.setAttribute("beginPageResults", 0);
	        session.setAttribute("endPageResults", 10);
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