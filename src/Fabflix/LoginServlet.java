package Fabflix;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;

import java.sql.*;

public class LoginServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	stmt = conn.prepareStatement("SELECT * FROM customers WHERE email = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
        	ResultSet rs = stmt.executeQuery();
        	if (rs.next()) {
                String message = "Sucess";
                request.setAttribute("message", message);
                request.getRequestDispatcher("index.jsp").forward(request,response);
                return;
        		//authenticate user session
        	}
        	
        } catch (SQLException se) { 
        	se.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        //If login fails
		String message = "Invalid login!";
		request.setAttribute("message", message);
		request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
    }
}