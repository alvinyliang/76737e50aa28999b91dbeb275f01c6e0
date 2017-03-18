package Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		PrintWriter out = response.getWriter();
		String rootPath = ((HttpServletRequest) request).getServletContext().getRealPath("/");
		
    	
		BufferedReader reader = new BufferedReader(new FileReader(rootPath + "/logs/TestLog.log"));
		
		String line;
	    while ((line = reader.readLine()) != null)
	    {
	      out.println(line);
	    }
	    reader.close();
//	    out.flush();
	    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
}
