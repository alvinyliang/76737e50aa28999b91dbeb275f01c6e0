package XMLParsers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class XMLParserServlet extends HttpServlet {
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		try{
			HttpSession session = request.getSession(false);
			
			InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
			DBConnection.connectDB(input);
			System.out.println(getServletContext().getRealPath("/"));
			String rootPath = getServletContext().getRealPath("/");
			
			XMLParser.parse(rootPath + "/XMLfiles/mains243.xml", rootPath + "/XMLfiles/actors63.xml",
					rootPath + "/XMLfiles/casts124.xml");
			
			DBConnection.disconnectDB();
			//TODO: print out feedbacks 
			request.getRequestDispatcher("/XMLParseReport.jsp").forward(request,response);
			
			
			
		} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
}
