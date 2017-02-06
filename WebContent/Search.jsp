<%
if (session.getAttribute("authenticated") == null) {
	request.getRequestDispatcher("login.jsp").forward(request,response);
}
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	
	
		<input type="text" placeholder="Search for movie" class="form-control" name="movie_title" id="search_movie_title">
		<input type="text" placeholder="Search by year" class="form-control" name="year" id="search_movie_year">
		<input type="text" placeholder="Search by director" class="form-control" name="director" id="search_movie_director">
		<input type="text" placeholder="Search by star" class="form-control" name="star" id="search_movie_star">
		
		<div style="padding-top:1%;"></div>

		
		<div class="container-fluid">
		<div id="myvariables">
		</div>
		
		<nav aria-label="Page navigation example">
			<ul class="pagination" id="pNumbers">
			
			</ul>
			
		</nav>			
		
				<div class="row">
					<div class="col-12">
					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
							<table class="table">
								<thead>
									<tr>
										<th></th>
										<th>Movie ID</th>
										<th>Title</th>
										<th>Year</th>
										<th>Director</th>
										<th>Staring</th>
									</tr>
								</thead>
							
								<tbody id="content"></tbody>
							</table>
							<div class="container"> </div>
						</div>
					</div>
					</div>
				</div>
			</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
	 	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
	
			function searchMovie(title, year, director, star, pageId){
			
				var html;
				var paginate_html = '';
				var numPage;
				var p = pageId;
				
				$.ajax({
					type: "POST",
					datatype: "json",
					url: "./Search",
					data: {"title": title,
							"year": year,
							"director": director,
							"star": star,
							"pageId": p},
					success: function(result){
	
						 html += "<tbody id='content'>";
						 
						 jQuery.each(result.movies, function(index, item) {
	
							 html += 
								 "<tr>" +
								 "<th scope='row'>" + "<img src='" + item.banner +  "' height='36' width='44'>"
							 	+ "<td class='align-middle'>" + item.id + "</td>"
								+ "<td class='align-middle'>" + item.title + "</td>"
							 	+ "<td class='align-middle'>" + item.year + "</td>"
							 	+ "<td class='align-middle'>" + item.director +  "</td>"
							 	+ "<td class='align-middle'>";
							 	
		            		 	jQuery.each(item.stars, function(index, star) {
		            		 		html += "<a href='Star?starId=" + star.id + "'>" + star.firstName + " " + star.lastName + " </a><br>";
		            		 	});
		            		 	
							 + "</td></tr>";
							 numPage = item.numPages;
							 
						 })
						 html += "</tbody>";
						 $('#content').replaceWith(html);
			         
				        //Loop through every available page and output a page link
				        if (numPage){
				        	var prev = p-1;
				        	var next = p+1;
				        	
				        	if (prev > 0){
					            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + prev + ')">Previous</a></li>';
				        	}
				        	else {
					            paginate_html += '<li class="page-item disabled"><a class="page-link" href="#" onclick="getPage(1)">Previous</a></li>';
				        	}
				        	
				        	for(var x = 1;  x <= numPage; x++){
				        		
				       			if (x == p){
						            paginate_html += '<li class="page-item active"><a class="page-link" href="#" onclick="getPage(' + x + ')">' + x+'</a></li>';

				       			}
				       			else{
						            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + x + ')">' + x+'</a></li>';

				       			}
				        		
					        }
				        	
				        	if (next > numPage){
					            paginate_html += '<li class="page-item disabled"><a class="page-link" href="#" onclick="getPage(' + numPage + ')">Next</a></li>';
				        	}
				        	else {
					            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + next + ')">Next</a></li>';

				        	}				        	
				        	
							 $('#pNumbers').html(paginate_html);					 
				        }
				        else{
							 $('#pNumbers').html("");					 

				        }
					}});
			}
			
		
		
		
			function getPage(page_num){
		        searchMovie($('input[name="movie_title"]').val(), 
		        		$('input[name="year"]').val(),
		        		$('input[name="director"]').val(),
		        		$('input[name="star"]').val(),
		        		page_num);
			}		
			
			$(document).ready(function () {

				  $("#search_movie_title, #search_movie_year, #search_movie_director, #search_movie_star").on("keyup", function(e) {
					    if(e.which == 13) {
					        searchMovie($('input[name="movie_title"]').val(), 
					        		$('input[name="year"]').val(),
					        		$('input[name="director"]').val(),
					        		$('input[name="star"]').val(),
					        		1);
					    }
					    

					});
				  
				});		

			

			    //Set up the page number links

			    //Load the first page
			    
		</script>

	</body>
</html>