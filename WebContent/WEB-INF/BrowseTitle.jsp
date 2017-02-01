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
									<a href='#' onclick="browseTitle('Z', '1', 'year', 'desc')">0</a>
									<a href='#' id='./Browse/Title?firstChar=1'>1</a>
									<a href='#' id='./Browse/Title?firstChar=2'>2</a>
									<a href='#' id='./Browse/Title?firstChar=3'>3</a>
									<a href='#' id='./Browse/Title?firstChar=4'>4</a>
									<a href='#' id='./Browse/Title?firstChar=5'>5</a>
									<a href='#' id='./Browse/Title?firstChar=6'>6</a>
									<a href='#' id='./Browse/Title?firstChar=7'>7</a>
									<a href='#' id='./Browse/Title?firstChar=8'>8</a>
									<a href='#' id='./Browse/Title?firstChar=9'>9</a>
									<a href='#' id='./Browse/Title?firstChar=a'>A</a>
									<a href='#' id='./Browse/Title?firstChar=b'>B</a>
									<a href='#' id='./Browse/Title?firstChar=c'>C</a>
									<a href='#' id='./Browse/Title?firstChar=d'>D</a>
									<a href='#' id='./Browse/Title?firstChar=e'>E</a>
									<a href='#' id='./Browse/Title?firstChar=f'>F</a>
									<a href='#' id='./Browse/Title?firstChar=g'>G</a>
									<a href='#' id='./Browse/Title?firstChar=h'>H</a>
									<a href='#' id='./Browse/Title?firstChar=i'>I</a>
									<a href='#' id='./Browse/Title?firstChar=j'>J</a>
									<a href='#' id='./Browse/Title?firstChar=k'>K</a>
									<a href='#' id='./Browse/Title?firstChar=l'>L</a>
									<a href='#' id='./Browse/Title?firstChar=m'>M</a>
									<a href='#' id='./Browse/Title?firstChar=n'>N</a>
									<a href='#' id='./Browse/Title?firstChar=o'>O</a>
									<a href='#' id='./Browse/Title?firstChar=p'>P</a>
									<a href='#' id='./Browse/Title?firstChar=q'>Q</a>
									<a href='#' id='./Browse/Title?firstChar=r'>R</a>
									<a href='#' id='./Browse/Title?firstChar=s'>S</a>
									<a href='#' id='./Browse/Title?firstChar=t'>T</a>
									<a href='#' id='./Browse/Title?firstChar=u'>U</a>
									<a href='#' id='./Browse/Title?firstChar=v'>V</a>
									<a href='#' id='./Browse/Title?firstChar=w'>W</a>
									<a href='#' id='./Browse/Title?firstChar=x'>X</a>
									<a href='#' id='./Browse/Title?firstChar=y'>Y</a>
									<a href='#' id='./Browse/Title?firstChar=z'>Z</a>
								</div>
							</div>
						<div id="content">Stuff to be replaced here</div>
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
		    	        	  location.reload();
		    	          }
		    	      });
		}
		
		</script>
	
 	
	</body>
</html>
