package Fabflix;


import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class UpdateCartServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	String action = request.getParameter("action");
    	String movieId = request.getParameter("movieId");
    	
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Movie movie = new Movie();
        try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
	        DatabaseQueries queries = new DatabaseQueries(conn);
	        movie = queries.getSimpleMovieDetails(movieId);
	         conn.close();
		} catch (Exception e) {

		}

    	if (action.equals("add")) {
    		Cart.addToCart(movieId, movie);
    	} else if (action.equals("remove")) {
    		Cart.removeFromCart(movieId);
    	} else if (action.equals("update")) {
        	int quantity = Integer.parseInt(request.getParameter("quantity").toString());
        	Cart.updateCart(movieId, quantity);
    	}
    	
    	String referer = request.getHeader("Referer");
    	try {
			response.sendRedirect(referer);
		} catch (IOException e) {

		}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
