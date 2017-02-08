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
				<c:if test="${not empty message}">
					<div class="alert alert-warning alert-dismissible fade show" role="alert">
					  
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    <span aria-hidden="true">&times;</span>
					  </button>
					  ${message}
					</div>		
				</c:if>
				<c:remove var="message" scope="session" />
				<div class="card">
					<div class="card-block">
						<h1 class="display-4">${movie.getTitle()}</h1>
					</div>
				</div>
				
					<div class="row pt-2">
						<div class="col-sm-6">
							<div class="card">
								<img class="card-img-top" src="${movie.getBanner()}" alt="Card image cap" style="height:500px; object-fit: contain">

							</div>
						</div>
						<div class="col-sm-6">
							<div class="card">
								<div class="card-block">
									<p><strong>Directed by: </strong>${movie.getDirector()}<br>
									<strong>Year: </strong>${movie.getYear()}<br>
									<strong>Id: </strong>${movie.getId()}<br>
									<strong>Genre: </strong><br>
										<c:set var="genres" value="${movie.getGenres()}"/>
										<c:forEach items="${genres}" var="entry">
										<a href="Browse/Genre?genre=${entry.value}">${entry.value}</a><br>
										</c:forEach>
									</p>
									<a href="${movie.getTrailer()}" class="btn btn-primary">Watch the trailer</a>
								</div>
							</div>
							<div class="card">
								<div class="card-block">
									<h3 class="card-title">Staring</h3>
									<c:set var="stars" value="${movie.getStars()}"/>
										<p>
										<c:forEach items="${stars}" var="star">
										<a href="Star?starId=${star.getId()}">${star.getName()}</a><br>
										</c:forEach>
										</p>
								</div>
							</div>
							<div class="card">
								<div class="card-block">
									<h3 class="card-title">On sale for $9.99!</h3>
									<form action="/Fabflix/UpdateCart" method="POST" id="addMovie">
										<input type="hidden" name="movieId" id="movieId" value="${movie.getId()}">
										<input type="hidden" name="action" value="add">
										<button id="addCart" class="btn btn-primary" onsubmit='return false;'>Add to Cart</button>
									</form>
								</div>
							</div>							
						</div>
					</div>

		    </div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		$(document).ajaxStart(function() {
		    $(document.body).css({'cursor' : 'wait'});
		}).ajaxStop(function() {
		    $(document.body).css({'cursor' : 'default'});
		});
		$(document).ready(function () {
			  $("#addCart").on("click", function() {
			        alert("Added movie to cart!");

				  	e.preventDefault();
					var movieId = $("#movieId").val();
					$.ajax({
						type: "POST",
						url: "/Fabflix/UpdateCart",
						data: {'movieId': movieId, 'action':'add'},
				        success: function(result){
				   
						}
					});
				}
			);
		});
		
		</script>

	</body>
</html>
