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
						<h3 class="display-3">Welcome to Zotflix</h3>
						<p>Hello, ${name}.</p>
						<div class="row">
							<div class="mx-auto" style="width: 800px">
						        <div class="input-group input-group-lg" style="vertical-align: middle;">
								
					            <input type="text" placeholder="Search for movie" class="form-control" name="movie_title" id="search_movie_title">
						            
		         				</div>
		         				<div class="pt-2">
			         				<div class="btn-group">
										<button type="button" class="btn btn-info" id="search_movie_button">Search</button>


									</div>
			         				<div class="btn-group">
	 									<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											Browse
										</button>
										<div class="dropdown-menu">
    										<a class="dropdown-item" href="Browse/Title">Browse by Title</a>
										    <div class="dropdown-divider"></div>
										    <a class="dropdown-item" href="Browse/Genre">Browse by Genre</a>
										</div>
									</div>					

								</div>
								
								
							</div>
						</div>
								<div id = "browse_index" hidden = true>
								
								
								</div>
								
								<div id="content"></div>
		
						
			    	</div>
				</div>
			</div>
		</div>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		<script type="text/javascript">
		

		
		</script>
		<script src="./JS/pageSearch.js"></script>
		
 	
	</body>
</html>
