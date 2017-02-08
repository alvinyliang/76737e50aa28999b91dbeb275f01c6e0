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
	
		
		<div style="padding-top:1%;"></div>

		
		<div class="container-fluid">
    <div class="row">
        <div class="span6" style="float: none; margin: 0 auto;">
			<form class="form-group form-inline">
			
		    <div class="input-group">
		          <span class="input-group-addon" id="basic-addon1">Search</span>
		  
					<input type="text" placeholder="Search by movie title" class="form-control" name="movie_title" id="search_movie_title" aria-describedby="basic-addon1">
					<input type="text" placeholder="Search by year" class="form-control" name="year" id="search_movie_year" aria-describedby="basic-addon1">
					<input type="text" placeholder="Search by director" class="form-control" name="director" id="search_movie_director" aria-describedby="basic-addon1">
					<input type="text" placeholder="Search by star" class="form-control" name="star" id="search_movie_star" aria-describedby="basic-addon1">	  
		    </div>
		    

		 	</form>
        </div>
    </div>	  
    
    
     <div class="row">
     
        <div class="span6" style="float: none; margin: 0 auto;">

			
		    <div class="input-group">
		  
  			<nav>
				<ul class="pagination" id="pNumbers"></ul>
			</nav>
			</div>
		</div>
		    

    </div>   
    
  	<style>
  	table {
  width: 100%;
}
th.movietitle{
	width: 30%
}
th.movieid, th.year {
  width: 5%
}
th.director, th.staring, th.genres {
  width: 15%; /* Not necessary, since only 70% width remains */
}
th.option{
	width: 10%;
}
  	</style>
  
	
		
		
				<div class="row">
					<div class="col-12">
					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
							<table class="table">
								<thead>
									<tr id="tableheaders">
										<th></th>
										<th class="movieid">ID</th>
										<th class="movietitle"><a href="#" id="titlesort">Title</a></th>
										<th class="year"><a href="#" id="yearsort">Year</a></th>
										<th class="director">Director</th>
										<th class="staring">Staring</th>
										<th class="genres">Genres</th>	
										<th class="option">
										    Show: <select class="form-control" id="selectLimit">
										      <option value="10">10</option>
										      <option value="25">25</option>
										      <option value="50">50</option>
										      <option value="100">100</option>
										    </select> 
		    							</th>
																			
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

			
			var lastPage = 1; 
			var lastSort = "title";
			var lastOrder = "asc";
			var p = 1;

			function searchMovie(pageId, sort, order){

				var html;
				var paginate_html = '';
				var tablehead_html = '';
				var numPage;
				
				var title = 
				
				$.ajax({
					type: "GET",
					datatype: "json",
					url: "./Search",
					data: {"title": $('input[name="movie_title"]').val(),
							"year": $('input[name="year"]').val(),
							"director": $('input[name="director"]').val(),
							"star": $('input[name="star"]').val(),
							"pageId": pageId,
							"sort": sort,
							"order": order,
							"limit": $('#selectLimit option:selected').val()
							},
					success: function(result){
			        	 			        	 
						 html += "<tbody id='content'>";
						 
						 jQuery.each(result.movies, function(index, item) {
	
							 html += 
								 "<tr>" +
								 "<th scope='row'>"
							 	+ "<td class='align-middle'>" + item.id + "</td>"
								+ "<td class='align-middle'>" + "<a href='Movie?movieId=" + item.id + "'>" + item.title +  "</a></td>"
							 	+ "<td class='align-middle'>" + item.year + "</td>"
							 	+ "<td class='align-middle'>" + item.director +  "</td>"
							 	+ "<td class='align-middle'>";
							 	
		            		 	jQuery.each(item.stars, function(index, star) {
		            		 		html += "<a href='Star?starId=" + star.id + "'>" + star.firstName + " " + star.lastName + " </a><br>";
		            		 	});
								 html += "</td>";
								 
								 html += "<td class='align-middle'>";

		            		 	jQuery.each(item.genres, function(index, genre) {
		            		 		html += '<a href="./Browse/Genre?genre=' + genre.genreName + '&page=1&sort=title&order=asc" >' + genre.genreName + " </a><br>";
		            		 	});
		            		 	
							 html += "</td></tr>";
							 numPage = item.numPages;
							 
						 })
						 html += "</tbody>";
						 $('#content').replaceWith(html);
						
						p = pageId;

				        //output a pagination
				        if (numPage){
				        	var prev = p-1;
				        	var next = p+1;
				        	
				        	if (prev > 0){
					            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + 
					            		prev + " , '" + sort + "' , '" + order + "')" + '">Previous</a></li>';
				        	}
				        	else {
					            paginate_html += '<li class="page-item disabled"><a class="page-link" href="#" onclick="getPage(' + 
					            		1 + " , '" + sort + "' , '" + order + "')" + '">Previous</a></li>';
				        	}
				        	for(var x = 1;  x <= numPage; x++){
				       			if (x == p){
						            paginate_html += '<li class="page-item active"><a class="page-link" href="#" onclick="getPage(' + 
						            		x + " , '" + sort + "' , '" + order + "')" + '">' + x +'</a></li>';
				       			}
				       			else{
						            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + 
						            		x + " , '" + sort + "' , '" + order + "')" + '">'  + x + '</a></li>';
				       			}
					        }
				        	if (next > numPage){
					            paginate_html += '<li class="page-item disabled"><a class="page-link" href="#" onclick="getPage(' + 
					            		numPage + " , '" + sort + "' , '" + order + "')" + '">Next</a></li>';
				        	}
				        	else {
					            paginate_html += '<li class="page-item"><a class="page-link" href="#" onclick="getPage(' + 
					            		next + " , '" + sort + "' , '" + order + "')" + '">Next</a></li>';
				        	}				        	
							 $('#pNumbers').html(paginate_html);					 
				        }
				        else{
							 $('#pNumbers').html("");					 
				        }
				        
						if (sort == lastSort) {
							order = (lastOrder === 'desc') ? 'asc' : 'desc';

						}
						
			        	 lastPage = p;
			        	 lastSort = sort;       	 
			        	 lastOrder = order;
				        
			        	 

						
					}});
			}
			
		
		
		
			function getPage(page_num, sort, order){
		        searchMovie(page_num, sort, order);
			}		


			$(document).ready(function () {
				$(document).ajaxStart(function() {
				    $(document.body).css({'cursor' : 'wait'});
				}).ajaxStop(function() {
				    $(document.body).css({'cursor' : 'default'});
				});					  
				  $("#search_movie_title, #search_movie_year, #search_movie_director, #search_movie_star").on("keyup", function(e) {
					    if(e.which == 13) {
					        searchMovie(1, "title", 'asc');
					    }
					});
				  
				  $("#selectLimit").change(function () { 
						searchMovie(1, 'title', 'asc');
					  });
				  
				  $("#titlesort").on("click", function() {
						searchMovie(p, "title", lastOrder);
					});				  
				  $("#yearsort").on("click", function() {
						searchMovie(p, "year", lastOrder);
					});	
				  

				  
				  
				  
				  
				});		

		

			    //Set up the page number links

			    //Load the first page
			    
		</script>

	</body>
</html>