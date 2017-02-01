package Fabflix;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MovieServlet extends HttpServlet {
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
        	
        	//Fabflix/Movie?mid=0000
            String movieID = request.getParameter("mid");
            
            
            
            // id, title, year, director, 
            // poster,  a link to its preview trailer
            // a list of stars (hyperlinked), and a list of genres (hyperlinked)
	        out.println("<br>");
	        
	        String html = "";
	        
            stmt = conn.prepareStatement("select * from movies where id=" + movieID + " ;");
         
        	ResultSet rs = stmt.executeQuery();
			
			
	        while (rs.next()){
	        	
	        	String title 	= rs.getString("title");
	        	String year 	= rs.getString("year");
	        	String director = rs.getString("director");
	        	String banner 	= rs.getString("banner_url");
	        	String trailer 	= rs.getString("trailer_url");
	        	
	        	
	        	
	        	
	        	String no_profile = "http://www.solarimpulse.com/img/profile-no-photo.png";
	        	// http://tc.sinaimg.cn/maxwidth.2048/tc.service.weibo.com/img_gmw_cn/f04fa3955b47aa8616838dc9a90749be.jpg
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
	    		
	        	html += "" 
	        			+"<div class='row'> \n" 
						+"		  <div class='col-sm-6'> \n"
						+"		   <img src='"+ banner +"' alt='' class='img-thumbnail'> \n"
						+"		  </div> \n"
						+"		  <div class='col-sm-6'> \n"
						+"		  	<h5 align='center'>"+ title +"</h5> \n"
						+"		  	<table class='table'> \n"
						+"			  <tbody> \n"
						+"			  	<tr> \n"
						+"			      <td>ID: </td> \n"
						+"			      <td>"+ movieID +"</td> \n"
						+"			    </tr> \n"
						+"			    <tr> \n"
						+"			      <td>Director: </td> \n"
						+"			      <td>"+ director +"</td> \n"
						+"			    </tr> \n"
						+"			    <tr> \n"
						+"			      <td>Year: </td> \n"
						+"			      <td>"+ year +"</td> \n"
						+"			    </tr> \n"
						+"			    <tr> \n"
						+"			      <td>Trailer: </td> \n";
						
				try{
			            URL url = new URL(trailer);
			            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			            connection.setRequestMethod("HEAD");
			            connection.setConnectTimeout(10);
			            
			            connection.connect();
			            int code = connection.getResponseCode();
			            if (code != HttpURLConnection.HTTP_OK){
			        		html += "			      <td>Unavailable</td> \n";
			            }else{
			            	html +="			      <td><a href = '"+ trailer +"'>Link</a></td> \n";
			            } 
			            
		        	}catch (Exception e){
		        		html += "			      <td>Unavailable</td> \n";
		        	}
						
				html+= "			    </tr> \n"
						+"				<tr> \n"
						+"					<td> Featuring: </td> \n"
						+"					<td> \n"
						+"						<ul class= 'list-group'> \n";
				
	        } 
	        
	        stmt = conn.prepareStatement(""
	        		+ "select stars.first_name, stars.last_name, stars.id "
	        		+ "from stars join stars_in_movies "
	        		+ "where (stars_in_movies.movie_id = " + movieID +" "
	        		+ "and stars.id = stars_in_movies.star_id);");
	        
	        rs = stmt.executeQuery();
	        
	        while (rs.next()){
	        	String starName = rs.getString("first_name")+" "+rs.getString("last_name");
	        	String starID = rs.getString("id");
	        	
	        	html += "					<li class='list-group-item'> "
	        			+ "<a href='#'>"+ starName +"</a></li> \n ";
	        
	        }
	        html +=   "						</ul> \n"
	        		+ "					</td> \n"
	        		+ "				</tr> \n"
	        		+ "				<tr> \n"
	        		+ "					<td>Genre: </td> \n"
	        		+ "					<td> \n"
	        		+ "						<ul class='list-group'> \n";
	        
	        
	        stmt = conn.prepareStatement("select genres.name, genres.id "
	        		+ "from genres join genres_in_movies "
	        		+ "where (genres_in_movies.movie_id = " + movieID + " "
	        		+ "and genres.id = genres_in_movies.genre_id);");
	        
	        rs = stmt.executeQuery();
	        
	        while(rs.next()){
	        	String genreName = rs.getString("name");
	        	String genreID = rs.getString("id");
	        	
	        	html += "					<li class='list-group-item'> "
	        			+ 	"<a href='#content' id='./Browse/Genre?gid=" + genreID 
	        			+	"'>"+ genreName + "</a></li> \n";
	        }
	        
	        
	        html +=   "						</ul> \n"
	        		+ "					</td> \n"
	        		+ "				</tr> \n"
	        		+ "			</tbody> \n" 
	        		+ "		</table> \n"
	        		+ "	</div> \n </div>\n" ; 
				
	        
	        System.out.println(html);
	        out.println(html);
	        
            
	       
        	
            
            
        	
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