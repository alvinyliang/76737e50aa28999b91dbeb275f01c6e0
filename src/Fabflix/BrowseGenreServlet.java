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
            
            System.out.println(stmt);
        	ResultSet rs = stmt.executeQuery();
			
	
	       
	        
	        	
        }
        catch (SQLException ex) {
            while (ex != null) {
                  System.out.println ("SQL Exception:  " + ex.getMessage ());
                  ex = ex.getNextException ();
              }  // end while
          }  // end catch SQLException
        catch(java.lang.Exception ex)
          {
        	

              
              return;
          }

    	
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

    


}
