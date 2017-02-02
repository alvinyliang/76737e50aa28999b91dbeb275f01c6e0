<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/CSS/narrow-jumbotron.css" rel="stylesheet">
		<title>Fabflix</title>
	</head>
	<body>
		<div class="container">
			<%@ include file="../navbar.jsp" %>
		</div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<div class="jumbotron" style="background:transparent !important">
						<h3 class="display-3">Welcome to Fabflix</h3>
						<p>Hello, ${username}.</p>
						<div class="row">
							<div class="mx-auto" style="width: 800px">
						        <div class="input-group input-group-lg" style="vertical-align: middle;">
								
					            <input type="text" placeholder="Search for movie" class="form-control" name="movie_title" id="search_movie_title">
						            
		         				</div>
		         				<div class="pt-2">
			         				<div class="btn-group">
										<button type="button" class="btn btn-info" id="search_movie_button">Search</button>
										<button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu">
											<a a href="/Search/Advanced" class="dropdown-item" href="#">Advanced Search Options</a>
										</div>
									</div>
			         				<div class="btn-group">
	 									<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											Browse
										</button>
										<div class="dropdown-menu">
    										<a class="dropdown-item" href="BrowseTitle.jsp">Browse by Title</a>
										    <div class="dropdown-divider"></div>
										    <a class="dropdown-item" href="#">Browse by Genre</a>
										</div>
									</div>					

								</div>
								
								
							</div>
						</div>
								<div id = "browse_index" hidden = true>
								
								
								</div>
								
								
								
								<div id="content">Stuff to be replaced here</div>
								
								<!-- Sample Single Movie Page -->
								<a href="#content" id='./Movie?mid=135001'>Sample Movie Page</a>
								
								
								
								
								
										
								<p> Showing ${results.size()} number of results </p>
								<p> ${countResults} total results found! </p>

								<c:if test="${results.size() > 0}">
								
									<nav aria-label="Page navigation example">
									 	<ul class="pagination">
								  		  	<li class="page-item"><a class="page-link" href="#${prev}" onclick="visitPage(${prev})">Previous</a></li>
												 <c:forEach var="x" begin="1" end="${numPages + 1}">
												 	<c:choose>
										  		  	    <c:when test="${x == pageId}">
    											  		  	<li class="page-item active"><a class="page-link" href="#${x}" onclick="visitPage(${x})">${x}</a></li>  	
													    </c:when>
													    <c:otherwise>
												  		  	<li class="page-item"><a class="page-link" href="#${x}" onclick="visitPage(${x})">${x}</a></li>  	
													    </c:otherwise>
												 	</c:choose>
												 </c:forEach>
								  		  	<li class="page-item"><a class="page-link" href="#${next}" onclick="visitPage(${next})">Next</a></li>
										  </ul>
									</nav>
								
								</c:if>		
								<ul class="list-group">
									<c:forEach var="item" items="${results}">
										<li class="list-group-item">${item['title']}, ${item['director']}</li>
										
										
									</c:forEach>
								</ul>	


							    <c:remove var="results" scope="session" />
								

								
							
								
						
			    	</div>
				</div>
			</div>
		</div>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		<script src="./JS/pageSearch.js"></script>
 	
	</body>
</html>
