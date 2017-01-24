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
import javax.servlet.http.HttpSession;

public class HomeServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
	        HttpSession session = request.getSession(false);  
	        if (session == null) {  
	    		String message = "Please login first!";
	    		request.setAttribute("message", message);
	    		request.getRequestDispatcher("login.jsp").forward(request,response);
	        }
	        else {
	        	//String username = (String) session.getAttribute("username");
	        	//request.setAttribute("username", username);
	     		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request,response);
	        }  
    	} catch (Exception e) {
    		
    	}
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request, response);
    }
}
