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
		String time = request.getParameter("time");
		String config = request.getParameter("config");
		System.out.println(time + " " + config);
    	
		BufferedReader reader = new BufferedReader(new FileReader(rootPath + "/logs/TestLog.log"));
		
		String header;
		String info;
	    while ((header = reader.readLine()) != null && (info = reader.readLine()) != null)
	    {
	    	// header: 	Mar 17, 2017 3:51:42 PM Logger.PerformanceLogger writeLog
    		// info:	INFO: type:TS	config:10000	auto_cmp:	title:Batman Begins	numQ:0	time:43.800476
	    	
	    	if (time != null){
	    		String[] splitedT = time.split(":");
	    		String[] splitedH = header.split(" ")[3].split(":");
	    		boolean passed = true;
	    		for (int i=0, j=0; i<splitedT.length; i++, j++){
	    			
//	    				System.out.println(splitedT[i] + " " + splitedH[j]);
	    				if (Integer.valueOf(splitedT[i]) >  Integer.valueOf(splitedH[j]) ){
	    					passed = false;
	    					break;
	    				}
	    			
	    		}
	    		if (! passed){ continue; }
	    	
	    	}
	    	if (config != null){
	    		String[] splitedI = info.split("\t")[1].split(":");
	    		if (! config.equalsIgnoreCase(splitedI[1])){
	    			continue;
	    		}
	    		
	    	}
	    	
	    	out.println(header + "\n" + info);
	    }
	    reader.close();
//	    out.flush();
	    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
}
