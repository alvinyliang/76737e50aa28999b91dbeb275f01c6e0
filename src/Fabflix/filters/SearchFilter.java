package Fabflix.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import Logger.PerformanceLogger;

public class SearchFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
			System.out.print(parameter+"="+request.getParameter(parameter)+"&");
		}
		String config = request.getParameter("config");
		String auto_cmp = request.getParameter("auto_complete");
		String title = request.getParameter("title");
		
		long startTime = System.nanoTime();
		/////////////////////////////////
		/// ** part to be measured ** ///
		
		
		
		chain.doFilter(request, response);
		
		
		
		/// ** part to be measured ** ///
		/////////////////////////////////
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
//		System.out.println("\nSearch Servlet Time: "+ ((double) elapsedTime/1000000)+" sec");
		
		String rootPath = ((HttpServletRequest) request).getServletContext().getRealPath("/");

    	PerformanceLogger.log(rootPath + "logs/TestLog.log", 
    			elapsedTime, 0, "TS", config, auto_cmp, title);
		

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	
	
	
}
