package Fabflix.employee;


import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Fabflix.Cart;
import Fabflix.DBConnection;
import Fabflix.DatabaseQueries;
import Fabflix.Movie;

import java.sql.*;

public class EmployeeActionsServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        
    	String action = request.getParameter("action");

    	
    	if (action.equals("addStar")) {
    		String firstName = request.getParameter("first_name");
    		String lastName = request.getParameter("last_name");
    		String dob = request.getParameter("dob");
    		String photoUrl = request.getParameter("photo_url");
    		
    		if (firstName == null)
    			firstName = "";
    		if (photoUrl == null)
    			photoUrl = "";
    		
            try {
            	//Convert input to java date then to SQL Date
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		java.util.Date formattedDob = null;
        		formattedDob = sdf.parse(dob);
    			java.sql.Date sqlDob = new java.sql.Date(formattedDob.getTime());
    			
    			
    			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    			//Connection conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
    			Connection conn = dbConn.getConnection();
	        	PreparedStatement stmt = conn.prepareStatement("INSERT into stars (first_name, last_name, dob, photo_url) VALUES (?, ?, ?, ?)");
				stmt.setString(1, firstName);
				stmt.setString(2, lastName);
				stmt.setDate(3, sqlDob);
				stmt.setString(4, photoUrl);
				stmt.executeUpdate();
    	        conn.close();
    		} catch (Exception e) {
    			request.setAttribute("message", "Error adding Star");
    			request.getRequestDispatcher("/Fabflix/_dashboard").forward(request,response);
    		}
    		
    	} else if (action.equals("addMovie")) {

            try {
    			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    			Connection conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
    			//Call stored procedure
    			CallableStatement cStmt = conn.prepareCall("{call add_movie(?, ?, ?, ?, ?, ?)}");
    			
    			
        		String title = request.getParameter("title");
        		int year = Integer.parseInt(request.getParameter("year"));
        		String director = request.getParameter("director");
        		
        		String star_first_name = request.getParameter("first_name");
        		String star_last_name = request.getParameter("last_name");
        		String genre = request.getParameter("genre");
        		
        		if (star_first_name.trim() == null){
        			star_first_name = "";
        		}
        		if (star_last_name.trim() == null){
        			star_last_name = "";
        		}
        		
 
        		cStmt.setString(1, title);
				cStmt.setInt(2, year);
				cStmt.setString(3, director);
				cStmt.setString(4, star_first_name);
				cStmt.setString(5, star_last_name);
				cStmt.setString(6, genre);
        		
    			
    		    boolean hadResults = cStmt.execute();
    	        conn.close();
    		} catch (Exception e) {

    		}
    		
    	}
    	
    	


        HttpSession session = request.getSession(true);
		session.setAttribute("message", "Sucess!");
		response.sendRedirect("/Fabflix/_dashboard");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
    
    private boolean addStar() {
    	return true;
    }
    
    private boolean addMovie() {
    	return true;
    }
}
