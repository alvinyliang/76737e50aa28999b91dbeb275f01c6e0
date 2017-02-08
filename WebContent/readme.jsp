<%--
if (session.getAttribute("authenticated") == null) {
	request.getRequestDispatcher("login.jsp").forward(request,response);
}
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
		<title>Fabflix</title>
	</head>
	<body>

		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<div class="jumbotron" style="background:transparent !important">
						<h3 class="display-3">README</h3>
						<h4>When cloning the project</h4>
						
							
								<ul>

									<li>Ensure MySQL Service is running on local machine.</li>
									<li>Configure WebContent/WEB-INF/db_config.properties</li>
									<li>Add External Jar -> MySQL Connector/J 6.0.5 to project buid path</li>
									<li>Create /lib directory under /WEB-INF and include the following files</li>
									<ul>
										<li>
											<a href="https://mvnrepository.com/artifact/mysql/mysql-connector-java/6.0.5">
										 	mysql-connector-java-6.0.5</a></li>
										 <li>
										 	<a href="https://mvnrepository.com/artifact/javax.servlet/jstl/1.2">
										 	jstl-1.2 </a>
										 </li>
										 <li>
										 	<a href="https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4">
										 	javax.json 1.0.4</a>
										 </li>
									</ul>
									<li>Under Eclipse "Problems" tab, change project JRE path 
									to match current Java Version. Current JRE is set to version _111.</li>
								</ul>	
									
								
						<h4>Project Dependencies</h4>
							<ul>
								<li>Tomcat v8.5 </li>
							</ul>
						<h4>To do</h4>
							<ul>
							</ul>
						<h4>Completed</h4>
							<ul>
								<li>Implement search</li>
								<li>Implement substring matching and partial matching (SQL queries with LIKE and ILIKE)</li>
								<li>Implement browsing</li>
								<li>Implement pagination for search and browse</li>
								<li>Implement movie and star info page</li>
								<li>Implement shopping cart and check out</li>
								<li>Implement order saving</li>
								<li>Implemented login page and login post to servlet</li>
								<li>Implemented Home page</li>
								<li>Implemented authenciate user in session</li>
								<li>Implemented session checking</li>
									<ul>
										<li>If user is logged in, login page redirects to home page</li>
									</ul>
								<li>Implement logout button (for session)</li>
							</ul>

						</div>

		

						
			    	</div>
				</div>
			</div>
		
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

	</body>
</html>
