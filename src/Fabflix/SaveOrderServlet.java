package Fabflix;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.SimpleDateFormat;

public class SaveOrderServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
	        HttpSession session = request.getSession(false);  
	        if (session.getAttribute("authenticated") == null) {  
	    		request.getRequestDispatcher("login.jsp").forward(request,response);
	    		return;
	        }
	        else {
	        	
	        	String firstName = (String) request.getParameter("firstName");
	        	String lastName = (String) request.getParameter("lastName");
	        	String cardNumber = (String) request.getParameter("cardNumber");
	        	String day = (String) request.getParameter("day");
	        	String month = (String) request.getParameter("month");
	        	String year = (String) request.getParameter("year");
	        	String userId = (String) session.getAttribute("userId");
	            InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
	            DBConnection dbConn = new DBConnection(input);
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        	Connection conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
	            
	        	DatabaseQueries dbQ = new DatabaseQueries(conn);

	        	if (saveOrder(firstName, lastName, cardNumber, day, month, year, dbQ))
	        	{
	        		dbQ.saveOrder(userId, Cart.cart);
	        		Cart.cleanCart();
        			request.setAttribute("message", "Thank you for your order!");
	        		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request,response);
	        	}
	        	else {
	        		request.setAttribute("message", "Please enter valid info!");
	        		request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request,response);
	        	}
	     		return;
	        }  
    	} catch (Exception e) {
    		
    	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request, response);
    }
    
    public Boolean saveOrder(String firstName, String lastName, String cardNumber, String day, String month, String year, DatabaseQueries dbQ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dob = null;
		try {
			dob = sdf.parse(year + "-" + month + "-" + day);
			if (dbQ.checkCreditCard(cardNumber, firstName, lastName, dob)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    }
}
