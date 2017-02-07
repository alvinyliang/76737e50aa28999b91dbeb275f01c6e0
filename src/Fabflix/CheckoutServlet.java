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

public class CheckoutServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
	        HttpSession session = request.getSession(false);  
	        if (session.getAttribute("authenticated") == null) {  
	    		request.getRequestDispatcher("./login.jsp").forward(request,response);
	    		return;
	        }
	        else {
	        	request.setAttribute("cart", Cart.cart);
	        	request.setAttribute("movies", Cart.movies);
	     		request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request,response);
	     		return;
	        }  
    	} catch (Exception e) {
    		
    	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request, response);
    }
}
