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
							<h3 class="display-3">Browse by Title</h3>
								<div id = "browse_index">
									<div id="browse_index_title">
										<a href='#' onclick="browseTitle('0', '1', 'year', 'desc')"><h4 style="display: inline">0</h3></a>
										<a href='#' onclick="browseTitle('1', '1', 'year', 'desc')"><h4 style="display: inline">1</h3></a>
										<a href='#' onclick="browseTitle('2', '1', 'year', 'desc')"><h4 style="display: inline">2</h3></a>
										<a href='#' onclick="browseTitle('3', '1', 'year', 'desc')"><h4 style="display: inline">3</h3></a>
										<a href='#' onclick="browseTitle('4', '1', 'year', 'desc')"><h4 style="display: inline">4</h3></a>
										<a href='#' onclick="browseTitle('5', '1', 'year', 'desc')"><h4 style="display: inline">5</h3></a>
										<a href='#' onclick="browseTitle('6', '1', 'year', 'desc')"><h4 style="display: inline">6</h3></a>
										<a href='#' onclick="browseTitle('7', '1', 'year', 'desc')"><h4 style="display: inline">7</h3></a>
										<a href='#' onclick="browseTitle('8', '1', 'year', 'desc')"><h4 style="display: inline">8</h3></a>
										<a href='#' onclick="browseTitle('9', '1', 'year', 'desc')"><h4 style="display: inline">9</h3></a>
										<a href='#' onclick="browseTitle('A', '1', 'year', 'desc')"><h4 style="display: inline">A</h3></a>
										<a href='#' onclick="browseTitle('B', '1', 'year', 'desc')"><h4 style="display: inline">B</h3></a>
										<a href='#' onclick="browseTitle('C', '1', 'year', 'desc')"><h4 style="display: inline">C</h3></a>
										<a href='#' onclick="browseTitle('D', '1', 'year', 'desc')"><h4 style="display: inline">D</h3></a>
										<a href='#' onclick="browseTitle('E', '1', 'year', 'desc')"><h4 style="display: inline">E</h3></a>
										<a href='#' onclick="browseTitle('F', '1', 'year', 'desc')"><h4 style="display: inline">F</h3></a>
										<a href='#' onclick="browseTitle('G', '1', 'year', 'desc')"><h4 style="display: inline">G</h3></a>
										<a href='#' onclick="browseTitle('H', '1', 'year', 'desc')"><h4 style="display: inline">H</h3></a>
										<a href='#' onclick="browseTitle('I', '1', 'year', 'desc')"><h4 style="display: inline">I</h3></a>
										<a href='#' onclick="browseTitle('J', '1', 'year', 'desc')"><h4 style="display: inline">J</h3></a>
										<a href='#' onclick="browseTitle('K', '1', 'year', 'desc')"><h4 style="display: inline">K</h3></a>
										<a href='#' onclick="browseTitle('L', '1', 'year', 'desc')"><h4 style="display: inline">L</h3></a>
										<a href='#' onclick="browseTitle('M', '1', 'year', 'desc')"><h4 style="display: inline">M</h3></a>
										<a href='#' onclick="browseTitle('N', '1', 'year', 'desc')"><h4 style="display: inline">N</h3></a>
										<a href='#' onclick="browseTitle('O', '1', 'year', 'desc')"><h4 style="display: inline">O</h3></a>
										<a href='#' onclick="browseTitle('P', '1', 'year', 'desc')"><h4 style="display: inline">P</h3></a>
										<a href='#' onclick="browseTitle('Q', '1', 'year', 'desc')"><h4 style="display: inline">Q</h3></a>
										<a href='#' onclick="browseTitle('R', '1', 'year', 'desc')"><h4 style="display: inline">R</h3></a>
										<a href='#' onclick="browseTitle('S', '1', 'year', 'desc')"><h4 style="display: inline">S</h3></a>
										<a href='#' onclick="browseTitle('T', '1', 'year', 'desc')"><h4 style="display: inline">T</h3></a>
										<a href='#' onclick="browseTitle('U', '1', 'year', 'desc')"><h4 style="display: inline">U</h3></a>
										<a href='#' onclick="browseTitle('V', '1', 'year', 'desc')"><h4 style="display: inline">V</h3></a>
										<a href='#' onclick="browseTitle('W', '1', 'year', 'desc')"><h4 style="display: inline">W</h3></a>
										<a href='#' onclick="browseTitle('X', '1', 'year', 'desc')"><h4 style="display: inline">X</h3></a>
										<a href='#' onclick="browseTitle('Y', '1', 'year', 'desc')"><h4 style="display: inline">Y</h3></a>
										<a href='#' onclick="browseTitle('Z', '1', 'year', 'desc')"><h4 style="display: inline">Z</h3></a>
									</div>
								</div>
					</div>
					
		<nav aria-label="Page navigation example">
			<ul class="pagination" id="pNumbers">
			</ul>
			
		</nav>			
					
					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
							<table class="table">
								<thead>
									<tr>
										<th></th>
										<th><a href='?title=${lastClick}&page=1&sort=title&order=${lastOrder}&count=${lastCount}'>Title</a></th>
										<th><a href='?title=${lastClick}&page=1&sort=year&order=${lastOrder}&count=${lastCount}'>Year</a></th>
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
									<li class="page-item ${pageInfo.getCurrentPage() == 1 ? 'disabled' : ''}">
							      		<a class="page-link" href="?title=${lastClick}&page=${pageInfo.getCurrentPage() - 1}&sort=${lastSort}&order=${lastOrder}&count=${lastCount}" aria-label="Previous">
							        		<span aria-hidden="true">&laquo;</span>
							        		<span class="sr-only">Previous</span>
							      		</a>
							    	</li>
							    	<c:forEach items="${pageInfo.getHelper()}" varStatus="loop">
            							<li class="page-item ${pageInfo.getCurrentPage() == loop.count ? 'active' : ''}"><a class="page-link" href="?title=${lastClick}&page=${loop.count}&sort=${lastSort}&order=${lastOrder}&count=${lastCount}">${loop.count}</a></li>
         							</c:forEach>
							    	<li class="page-item ${pageInfo.getCurrentPage() == pageInfo.getPages() ? 'disabled' : ''}">
							      		<a class="page-link" href="?title=${lastClick}&page=${pageInfo.getCurrentPage() + 1}&sort=${lastSort}&order=${lastOrder}&count=${lastCount}" aria-label="Next">
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
			window.location = "?title=" + "${lastClick}" + "&page=1" + "&sort=" + "${lastSort}" + "&order=" + "${lastOrder}" + "&count=" + count;
		})
		
		$(document).ajaxStart(function() {
		    $(document.body).css({'cursor' : 'wait'});
		}).ajaxStop(function() {
		    $(document.body).css({'cursor' : 'default'});
		});
		
		var lastPage = 1; 
		var lastSort = "title";
		var lastOrder = "asc";
		var p = 1;		
		
		function browseTitle(title, page, sort, order) {
			window.location = "?title=" + title + "&page=" + page + "&sort=" + sort + "&order=" + order;	
		
		}
		
		</script>

	</body>
</html>
