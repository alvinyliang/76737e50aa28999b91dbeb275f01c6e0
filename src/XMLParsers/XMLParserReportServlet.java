package XMLParsers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XMLParserReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {


	    	//String username = (String) session.getAttribute("username");
	    	//request.setAttribute("username", username);
	 		request.getRequestDispatcher("/XMLParseReport.jsp").forward(request,response);
	 		return;
	         
    	} catch (Exception e) {
    		
    	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request, response);
    }

}
