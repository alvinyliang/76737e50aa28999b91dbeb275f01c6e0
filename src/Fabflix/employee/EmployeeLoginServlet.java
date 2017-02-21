package Fabflix.employee;



import java.io.IOException;
import java.io.InputStream;
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
import java.util.ArrayList;
import java.util.HashMap;

import Fabflix.DBConnection;

public class EmployeeLoginServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // get reCAPTCHA request param
 		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
 		//boolean valid = VerifyUtils.verify(gRecaptchaResponse);
 		boolean valid = true;
        
        
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        HttpSession session = request.getSession(true);
        
        try {
        	
        	if (valid){
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
	        	stmt = conn.prepareStatement("SELECT * FROM employees WHERE email = ? AND password = ?");
				stmt.setString(1, username);
				stmt.setString(2, password);
	        	ResultSet rs = stmt.executeQuery();
	        	
	        	if (rs.next()) {
	        		String name = rs.getString("fullname");
	        		session.setAttribute("employeeName", name);
	        		session.setAttribute("employeeAuthenticated", "true");
	        		rs.close();
	                response.sendRedirect("/Fabflix/_dashboard");
	                return;
	        	}
        	}
        	
        } catch (SQLException se) { 
        	se.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        //If login fails
		String message = "Invalid login!";
		
		//check recaptcha
		if (!valid) {
			message = "You missed the Captcha.";
		} 
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("employeelogin.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		request.getRequestDispatcher("employeelogin.jsp").forward(request,response);
    }
    
    
}