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
		<title>Fabflix</title>
	</head>
	<body>
		<div class="container">
			<%@ include file="../navbar.jsp" %>
		</div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-12">

					<div class="text-center">
							<h3 class="display-3"><a data-toggle="collapse" data-target="#genreContainer" href="#genreContainer" >Browse by Genre</a> <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#genreContainer">Show/Hide Genres</button></h3>

								<div id = "browse_index">

								</div>
					</div>
					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
							<div id="genreContainer" class="collapse">
								<table class="table" id="genreListTable">
									<tbody id="genreList">
									</tbody>
								</table>
							</div>
							<table class="table">
								<thead>
									<tr>
										<th></th>
										<th><a href='#' onclick="browseGenre(lastGenre, '1', 'title', lastOrder)">Title</a></th>
										<th><a href='#' onclick="browseGenre(lastGenre, '1', 'year', lastOrder)">Year</a></th>
										<th>Director</th>
										<th>Staring</th>
									</tr>
								</thead>
								<tbody id="content">
								</tbody>
							</table>
						<div class = "container ">
							
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		var lastGenre, lastPage, lastSort, lastOrder;
		$(document).ready(function(){
			$.ajax({
					type: "POST",
					url: "/Fabflix/Browse/Menu",
			        success: function(result){
			        	var html = "<tbody id='genreList'>";
			             jQuery.each(result.genres, function(index, item) {
			            	 html += "<tr><td><a class='genre' href='#'>" + item.name + "</a></td></tr>";
			             });
			             html += "</tbody>";
				         $('#genreList').replaceWith(html);
					}
			});
		});
		

		$(document).on('click', "a.genre", function(event) {
			var genre = $(event.target).text();
			$('.collapse').collapse('hide'); //Hide Genre list
			browseGenre(genre, '1', 'year', 'desc');    
		});
		
		function browseGenre(genre, page, sort, order) {
			if (sort == lastSort) {
				order = (lastOrder === 'desc') ? 'asc' : 'desc';
			}

       	 
			var html = "<tbody id= 'content'>";
			$.ajax(
			     {
			         type: "POST",
			         url: '/Fabflix/Browse/Genre',
			         data: { 'genre': genre,  'page': page, 'sort':sort, 'order':order},
			         success: function (result)
			         {			        	 
			        	 lastGenre = genre;
			        	 lastPage = page;
			        	 lastOrder = order;
			        	 lastSort = sort;
			        	 
			             jQuery.each(result.movies, function(index, item) {
			            	 html += 
			            		 "<tr><th scope='row'>" + "<img src='" + item.banner +  "' width='125' height='187'>"
			            		 	+ "<td class='align-middle'>" + item.title + "</td>"
			            		 	+ "<td class='align-middle'>" + item.year + "</td>"
			            		 	+ "<td class='align-middle'>" + item.director + "</td>"
			            		 	+ "<td class='align-middle'>" + item.director + "</td>"
			            		 	+ "<td class='align-middle'>";
			            		 	jQuery.each(item.stars, function(index, star) {
			            		 		html += "<a href='Star?starId=" + star.id + "'>" + star.firstName + " " + star.lastName + " </a><br>";
			            		 	});
			            		 	
			            		 	html += "</td></tr>";
			             })

			             html += "</tbody>"
			             $('#content').replaceWith(html);
			         }
			     });
           
		}

		
		</script>

	</body>
</html>
