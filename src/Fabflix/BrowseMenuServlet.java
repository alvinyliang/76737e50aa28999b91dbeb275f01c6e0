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

public class BrowseMenuServlet extends HttpServlet {
	
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
        	
        	
            
            
            String html = "";
            
	        

	        stmt = conn.prepareStatement("select distinct substring(movies.title from 1 for 1)"
	        		+ " as first_char from movies "
	        		+ "order by movies.title;");
	        

			ResultSet rs = stmt.executeQuery();
			
			html +=		"<div>\n";
			html += 	"	<h5 align='center'>Browse by Title</h5>\n";
			
	        while (rs.next()){
	        	String firstChar = rs.getString("first_char");
	        	html += "	<a href='#content' id='./Browse/Title?fchar="+ firstChar + "'>"+ firstChar+ "</a>\n";
	        	out.println();
	        
	        }
	        
	        
	        html += 	"</div>\n";
            
            
        	stmt = conn.prepareStatement("select * "
        			+ "from genres order by genres.name;");
        	rs = stmt.executeQuery();
			html += 	"<div>\n";
			html += 	"	<h5 align='center'>Browse by Genre</h5>\n";
			
	        while (rs.next()){
	        	String genre = rs.getString("name");
	        	String gid = rs.getString("id"); 
	        	html += "	<a href='#content' id='./Browse/Genre?gid="+ gid + "'>"+ genre+ "</a>\n";
	        	out.println();
	        
	        }
	        
	        html += 	"</div>\n";
            
	       
	        
	        
	        System.out.println(html);
	        System.out.println("8:01");
	        out.print(html);
	        	
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
