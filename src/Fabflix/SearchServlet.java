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
import java.util.ArrayList;
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
        
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        HttpSession session = request.getSession(true);
        int queryLimit = 25;

        try{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE title LIKE ? ORDER BY title ASC LIMIT ? OFFSET ? ");
        	PreparedStatement stmt2 = conn.prepareStatement("SELECT COUNT(*) FROM movies WHERE title LIKE ?");
        	
	        //match movie_title searched for
        	
    		String movie_title = (String) session.getAttribute("movie_title");
    		if (request.getParameter("movie_title") != null){
    			movie_title = request.getParameter("movie_title");
    		};
        	
	        if (movie_title == null || movie_title.isEmpty()){
	        	stmt.setString(1, movie_title);	
	        	stmt2.setString(1, movie_title);	
	        }
	        else{
	        	stmt.setString(1, '%' + movie_title + '%');
	        	stmt2.setString(1, '%' + movie_title + '%');
	        }

	        //number of results to return
        	stmt.setInt(2, queryLimit);

        	//offest by pageId
        	int pageId;
    		if (request.getParameter("pageId") != null){
    			pageId = Integer.parseInt(request.getParameter("pageId"));
    		}
    		else{
    			pageId = 1;
    		}
    		
    		if (pageId == 1){
		        stmt.setInt(3, 0);
    		}
    		else{
	        	stmt.setInt(3, (pageId-1)*queryLimit); //offset results by pageId user is currently on
    		}

	        //execute table results and count query
			ResultSet rs = stmt.executeQuery();
			ResultSet rs2 = stmt2.executeQuery();
			
			rs2.next();
			int countResults = rs2.getInt(1);
			
			ArrayList<Hashtable<String, String>> sql_results = new ArrayList<Hashtable<String, String>>();
        	
			String no_profile = "http://www.solarimpulse.com/img/profile-no-photo.png";

			//key for hashtable
			while (rs.next()){
				
				Hashtable<String, String> to_return = new Hashtable<String, String>();
				
	        	String title = rs.getString(2);
	        	String director = rs.getString(4);
	        	String banner = rs.getString(5);

	        	//check for valid banner link
	        	if (!validURL(banner)){
	        		banner = no_profile;
	        	}
	    		
	        	to_return.put("title", title);
	        	to_return.put("director", director);
	        	to_return.put("banner", banner);
	        	
				sql_results.add(to_return);				
	        } 
	        
			
			int numPages = (int)Math.ceil(countResults/queryLimit);

			if (pageId - 1 > 0){
				session.setAttribute("prev", pageId-1);
			}
			else{
				session.setAttribute("prev", 1);
				}
			
			if (pageId + 1 <= numPages){
				session.setAttribute("next", pageId+1);
			}
			else{
				session.setAttribute("next", numPages+1);
			}
			
			
			session.setAttribute("results", sql_results);
	        session.setAttribute("countResults", countResults);
            session.setAttribute("movie_title", movie_title);
            session.setAttribute("pageId", pageId);
            session.setAttribute("numPages", numPages);

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