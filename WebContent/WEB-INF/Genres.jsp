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
										<th><a href='?genre=${lastGenre}&page=1&sort=title&order=${lastOrder}&count=${lastCount}'>Title</a></th>
										<th><a href='?genre=${lastGenre}&page=1&sort=year&order=${lastOrder}&count=${lastCount}'>Year</a></th>
										<th>Director</th>
										<th>Staring</th>
										<th>Genres</th>
										
									</tr>
								</thead>
								<tbody id="content">
									<c:forEach items="${movies}" var="movie">
										<tr><th scope="row"><img src="${movie.banner}" width="125" height = "187">
											<td class="align-middle"><a href="../Movie?movieId=${movie.id}">${movie.title}</a></td>
											<td class="align-middle">${movie.year}</td>
											<td class="align-middle">${movie.director}</td>
											<td class="align-middle">
												<c:forEach items="${movie.stars}" var="star">
													<a href="../Star?starId=${star.id}"> ${star.getName()}</a><br>
												</c:forEach>
											</td>
											<td class="align-middle">
												<c:forEach items="${movie.genres}" var="genre">
													${genre.value}<br>
												</c:forEach>											
											
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<c:if test="${pageInfo.getPages() > 0}">
							<c:set var="end" value="${pageInfo.getPages()+1}"/>
							<nav aria-label="Page navigation example">
								<ul class="pagination">
									<li class="page-item">
							      		<a class="page-link" href="?genre=${lastGenre}&page=${pageInfo.getCurrentPage() - 1}&sort=${lastSort}&order=${lastOrder}" aria-label="Previous">
							        		<span aria-hidden="true">&laquo;</span>
							        		<span class="sr-only">Previous</span>
							      		</a>
							    	</li>
							    	<c:forEach items="${pageInfo.getHelper()}" varStatus="loop">
            							<li class="page-item ${pageInfo.getCurrentPage() == loop.count ? 'active' : ''}"><a class="page-link" href="?genre=${lastGenre}&page=${loop.count}&sort=${lastSort}&order=${lastOrder}">${loop.count}</a></li>
         							</c:forEach>
							    	<li class="page-item">
							      		<a class="page-link" href="?genre=${lastGenre}&page=${pageInfo.getCurrentPage() + 1}&sort=${lastSort}&order=${lastOrder}" aria-label="Next">
							        		<span aria-hidden="true">&raquo;</span>
							        			<span class="sr-only">Next</span>
							      		</a>
						    		</li>
						    		<li>
						    			<span>
						    			&nbsp;Show:
										<select id="show">
											<option value="5" ${lastCount == '5' ? 'selected' : ''}>5</option>
										  	<option value="10" ${lastCount == '10' ? 'selected' : ''}>10</option>
										  	<option value="25" ${lastCount == '25' ? 'selected' : ''}>25</option>
										 	<option value="50" ${lastCount == '50' ? 'selected' : ''}>50</option>
										</select>
										</span>
						    		</li>
						  		</ul>
							</nav>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		$('#show').on('change', function() {
			var e = document.getElementById("show");
			var count = e.options[e.selectedIndex].value;
			window.location = "?genre=" + "${lastGenre}" + "&page=1" + "&sort=" + "${lastSort}" + "&order=" + "${lastOrder}" + "&count=" + count;
		})
			
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
			window.location = "?genre=" + genre + "&page=" + page + "&sort=" + sort + "&order=" + order;
		}

		
		</script>

	</body>
</html>
