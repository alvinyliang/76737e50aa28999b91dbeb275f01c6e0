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
										<button type="button" class="btn btn-info" id="browse_movie_button">Browse</button>
										
									</div>
									 
																	
								</div>
								
								
							</div>
						</div>
								<div id = "browse_index" hidden = true>
									<h5 align = "center">Browse by Title</h5> 
									<div  id="browse_index_title">
									
										<a href='#' id='./Browse/Title?firstChar=0'>0</a>
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
								
									<h5 align = "center">Browse by Genre</h5>
									<div > 
									
										<a href='#' id='./Browse/Genre?genreName=Adv'>Adventure</a>
										<a href='#' id='./Browse/Genre?genreName=Animation'>Animation</a>
										<a href='#' id='./Browse/Genre?genreName=Arts'>Arts</a>
										<a href='#' id='./Browse/Genre?genreName=Classic'>Classic</a>
										<a href='#' id='./Browse/Genre?genreName=Comedy'>Comedy</a>
										<a href='#' id='./Browse/Genre?genreName=Crime'>Crime</a>
										<a href='#' id='./Browse/Genre?genreName=Detective'>Detective</a>
										<a href='#' id='./Browse/Genre?genreName=Documentary'>Documentary</a>
										<a href='#' id='./Browse/Genre?genreName=Drama'>Drama</a>
										<a href='#' id='./Browse/Genre?genreName=Epics'>Epics</a>
										<a href='#' id='./Browse/Genre?genreName=Family'>Family</a>
										<a href='#' id='./Browse/Genre?genreName=Fantasy'>Fantasy</a>
										<a href='#' id='./Browse/Genre?genreName=Film-Noir'>Film-Noir</a>
										<a href='#' id='./Browse/Genre?genreName=Foreign'>Foreign</a>
										<a href='#' id='./Browse/Genre?genreName=Gangster'>Gangster</a>
										<a href='#' id='./Browse/Genre?genreName=Horror'>Horror</a>
										<a href='#' id='./Browse/Genre?genreName=Indie'>Indie</a>
										<a href='#' id='./Browse/Genre?genreName=James'>James</a>
										<a href='#' id='./Browse/Genre?genreName=Kid'>Kid</a>
										<a href='#' id='./Browse/Genre?genreName=Love'>Love</a>
										<a href='#' id='./Browse/Genre?genreName=Musi'>Musical</a>
										<a href='#' id='./Browse/Genre?genreName=Mystery'>Mystery</a>
										<a href='#' id='./Browse/Genre?genreName=Roman'>Roman</a>
										<a href='#' id='./Browse/Genre?genreName=Romance'>Romance</a>
										<a href='#' id='./Browse/Genre?genreName=SciFi'>SciFi</a>
										<a href='#' id='./Browse/Genre?genreName=Short'>Short</a>
										<a href='#' id='./Browse/Genre?genreName=Spy'>Spy</a>
										<a href='#' id='./Browse/Genre?genreName=Suspense'>Suspense</a>
										<a href='#' id='./Browse/Genre?genreName=Thriller'>Thriller</a>
										<a href='#' id='./Browse/Genre?genreName=Tragedy'>Tragedy</a>
										<a href='#' id='./Browse/Genre?genreName=War'>War</a>
										<a href='#' id='./Browse/Genre?genreName=Western'>Western</a>
									</div>
								</div>
								
								<div id="content">Stuff to be replaced here</div>	
								
										
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
