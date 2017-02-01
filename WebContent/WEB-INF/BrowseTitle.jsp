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
						<h3 class="display-3">Browse by Title</h3>
						<div class="row">
						</div>
							<div id = "browse_index">
								<div  id="browse_index_title">
									<a href='#' onclick="browseTitle('0', '1', 'year', 'desc')">0</a>
									<a href='#' onclick="browseTitle('1', '1', 'year', 'desc')">1</a>
									<a href='#' onclick="browseTitle('2', '1', 'year', 'desc')">2</a>
									<a href='#' onclick="browseTitle('3', '1', 'year', 'desc')">3</a>
									<a href='#' onclick="browseTitle('4', '1', 'year', 'desc')">4</a>
									<a href='#' onclick="browseTitle('5', '1', 'year', 'desc')">5</a>
									<a href='#' onclick="browseTitle('6', '1', 'year', 'desc')">6</a>
									<a href='#' onclick="browseTitle('7', '1', 'year', 'desc')">7</a>
									<a href='#' onclick="browseTitle('8', '1', 'year', 'desc')">8</a>
									<a href='#' onclick="browseTitle('9', '1', 'year', 'desc')">9</a>
									<a href='#' onclick="browseTitle('A', '1', 'year', 'desc')">A</a>
									<a href='#' onclick="browseTitle('B', '1', 'year', 'desc')">B</a>
									<a href='#' onclick="browseTitle('C', '1', 'year', 'desc')">C</a>
									<a href='#' onclick="browseTitle('D', '1', 'year', 'desc')">D</a>
									<a href='#' onclick="browseTitle('E', '1', 'year', 'desc')">E</a>
									<a href='#' onclick="browseTitle('F', '1', 'year', 'desc')">F</a>
									<a href='#' onclick="browseTitle('G', '1', 'year', 'desc')">G</a>
									<a href='#' onclick="browseTitle('H', '1', 'year', 'desc')">H</a>
									<a href='#' onclick="browseTitle('I', '1', 'year', 'desc')">I</a>
									<a href='#' onclick="browseTitle('J', '1', 'year', 'desc')">J</a>
									<a href='#' onclick="browseTitle('L', '1', 'year', 'desc')">L</a>
									<a href='#' onclick="browseTitle('M', '1', 'year', 'desc')">M</a>
									<a href='#' onclick="browseTitle('N', '1', 'year', 'desc')">N</a>
									<a href='#' onclick="browseTitle('O', '1', 'year', 'desc')">O</a>
									<a href='#' onclick="browseTitle('P', '1', 'year', 'desc')">P</a>
									<a href='#' onclick="browseTitle('Q', '1', 'year', 'desc')">Q</a>
									<a href='#' onclick="browseTitle('R', '1', 'year', 'desc')">R</a>
									<a href='#' onclick="browseTitle('S', '1', 'year', 'desc')">S</a>
									<a href='#' onclick="browseTitle('T', '1', 'year', 'desc')">T</a>
									<a href='#' onclick="browseTitle('U', '1', 'year', 'desc')">U</a>
									<a href='#' onclick="browseTitle('V', '1', 'year', 'desc')">V</a>
									<a href='#' onclick="browseTitle('W', '1', 'year', 'desc')">W</a>
									<a href='#' onclick="browseTitle('X', '1', 'year', 'desc')">X</a>
									<a href='#' onclick="browseTitle('Y', '1', 'year', 'desc')">Y</a>
									<a href='#' onclick="browseTitle('Z', '1', 'year', 'desc')">Z</a>
								</div>
							</div>
							<div id="content">
							
							</div>
						<ul class="list-group">
							<c:forEach var="item" items="${movieList}">
								<li class="list-group-item">${item.getTitle()}</li>
							</c:forEach>
						</ul>	
			    	</div>
				</div>
			</div>
		</div>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		function browseTitle(title, page, sort, order) {
		      $.ajax(
		    	      {
		    	          type: "POST",
		    	          url: '\Title',
		    	          data: { 'title': title,  'page': page, 'sort':sort, 'order':order},
		    	          success: function (result)
		    	          {
		    	        	  var nhtml = '';
		    	        	  nhtml += "<ul class='list-group'>";
		    	              jQuery.each(result.movies, function(index, item) {
			    	              alert(item.title);
		    	                  nhtml += "<li>" + item.title + "</li>";
		    	              });
		    	              nhtml += "</ul>";
		    	              $('#content').replaceWith(nhtml);
		    	          }
		    	      });
		}
		
		</script>
	
 	
	</body>
</html>
