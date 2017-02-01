<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
						        
					            <input type="text" placeholder="Search for movie" class="form-control" name="movie_title" id="search_movie">
						            
						            
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
										<!-- 
										
										<button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu" id="browse_dropdown">
											<a href="Browse?title=true&genre=false" class="dropdown-item" id="browse_movie_title">Browse by Title</a>
											<div class="dropdown-divider"></div>
											<a a href="Browse?title=false&genre=true" class="dropdown-item" href="#" id="browse_movie_genre">Browse by Genre</a>
										</div>
										 -->
									</div>
									 
																	
								</div>
								
								
							</div>
						</div>
								<div id = "browse_index" hidden = true>
								
								
								</div>
								
								<div id="content">Stuff to be replaced here</div>
								

						
			    	</div>
				</div>
			</div>
		</div>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		$.ajax({
	  		type: "POST",
	  		url: "./Browse/Menu",
	  		success: function(result){
		  		$("#browse_index").html(result);
		  	}
	  	});
		$(document).ready(function () {
			  var searchResult = function(){
					$.ajax({
		                type: "GET",
						url:"./Search",
		                data: {"movie_title" : $('input[name="movie_title"]').val()},
						success:function(result){
				    		$("#content").html(result);
				    	}
					});
					
			  };
			  
			  
			  
				  
			  $("#search_movie_button").on("click", searchResult);
			  $("#search_movie").on("keyup", searchResult);
			  
			  $("#browse_movie_button").click(function (){
				  $("#content").html("<p></p>")
				  $("#browse_index").attr("hidden", false);
			  });
			  
			  
			  $("[id^='./Browse']").click(function(){
				  
				  $.ajax({
		                type: "GET",
						url: $ (this).attr('id'),
						data: {"order": "yd",
							   "p": 1, 
							   "m": 10},
						success: function(result){
				    		$("#content").html(result);
				    	}
					});
				  
			  });
			  
			  
			  
			  
			 
			  
		});
		
		</script>
	
 	
	</body>
</html>
