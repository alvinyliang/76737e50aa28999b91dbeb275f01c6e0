package Fabflix;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateCartServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	String action = request.getParameter("action");
    	String movieId = request.getParameter("movieId");
    	
    	if (action.equals("add")) {
    		Cart.addToCart(movieId);
    	} else if (action.equals("remove")) {
    		Cart.removeFromCart(movieId);
    	} else if (action.equals("update")) {
        	int quantity = Integer.parseInt(request.getParameter("quantity").toString());
        	Cart.updateCart(movieId, quantity);
    	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
