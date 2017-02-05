<%
if (session.getAttribute("authenticated") == null) {
	request.getRequestDispatcher("login.jsp").forward(request,response);
}
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/CSS/narrow-jumbotron.css" rel="stylesheet">
		<title>Zotflix</title>
	</head>
	<body>
		<div class="container">
			<%@ include file="../navbar.jsp" %>
		</div>

		<div class="container">
				<div class="card">
					<div class="card-block">
						<h1 class="display-4">${star.getName()}</h1>
					</div>
				</div>
				
					<div class="row pt-2">
						<div class="col-sm-6">
							<div class="card">
								<img class="card-img-top" src="${star.getPhoto()}" alt="Card image cap" style="height:500px; object-fit: contain">

							</div>
						</div>
						<div class="col-sm-6">
							<div class="card">
								<div class="card-block">
									<p><strong>Date of Birth: </strong>${star.getDob()}<br>
									<strong>Id: </strong>${star.getId()}<br>
									</p>
								</div>
							</div>
							<div class="card">
								<div class="card-block">
									<h3 class="card-title">Starred In</h3>
									<p>
										<c:set var="movies" value="${star.getMovies()}"/>
										<c:forEach items="${movies}" var="entry">
										<a href="Movie?movieId=${entry.key}">${entry.value}</a><br>
										</c:forEach>
									</p>
								</div>
							</div>							
						</div>
					</div>

		    </div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">

		
		</script>

	</body>
</html>
