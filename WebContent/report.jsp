<%--
if (session.getAttribute("authenticated") == null) {
	request.getRequestDispatcher("login.jsp").forward(request,response);
}
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
		<title>Fabflix</title>
	</head>
	<body>

		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<div class="jumbotron" style="background:transparent !important">
						<h3 class="display-3">Zotflix Like Predicate Report</h3>

						<div class="input-group">
								<ul>
									<li> We used the SQL LIKE clause wildcard operator (%) to string match movies, primarily in: </p></li>
									<ul>
										<li>SearchServlet.java</li>
										<ul>
										<li>    	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE "
    			+ "(title LIKE ? AND year LIKE ? AND director LIKE ? AND "
    			+ "id IN "
    			+ "(SELECT movie_id FROM stars_in_movies WHERE star_id IN "
    			+ "(SELECT id FROM moviedb.stars WHERE concat(first_name, ' ', last_name) "
    					+ "LIKE ?))) "
    			+ "ORDER BY movies." + sort + " " + order + " LIMIT ? OFFSET ? ");</li>
    			
    									<ul>
    										<li>For obtaining the movie list from searching, we used this query. We performed a fuzzy match on each parameter and used the AND predicate for multiple search combinations. </li>
    										<li>It was easy to get match combinations of movie title, director, and year because these were all in one table. To match combination with a star's name took more effort, having to nest multiple select queries to join other tables and then use IN operator to return a boolean. We then used that result to add it as a combination to search by movie title, year, director, and/or star's name.
    									</ul>
<li>
    	if ((title == null || title.trim().isEmpty()) && <br>
    			(director == null || director.trim().isEmpty()) && <br>
    			(year == null || year.trim().isEmpty()) && <br>
    			(star == null || star.trim().isEmpty()) <br>
    			){ <br>
        	stmt.setString(1, ""); <br>
        	stmt.setString(2, ""); <br>
        	stmt.setString(3, ""); <br>
        	stmt.setString(4, ""); <br>
        } <br>
    	else{ <br>
            stmt.setString(1, '%' + title + '%'); <br>
        	stmt.setString(2, '%' + year + '%'); <br>
        	stmt.setString(3, '%' + director + '%'); <br>
        	stmt.setString(4, '%' + star + '%'); <br>
    	} <br>
    	
    	
</li>				

<ul>
	<li>We used a general "%%" wildcard for matching. To set the prepared statement above, we checked if all parameters were empty, so all results would not display back to the user, else we performed a fuzzy match on all parameters. These two pieces of code were used in the same function. We used LIKE in this fashion so we did not have to write multiple functions and queries to obtain the movie list.</li>
</ul>						
										
										
										</ul>
									</ul>	
									<li> In some cases we used the substring(expression, start, length) function for matching. </p></li>
									<ul>
										<li>BrowseTileServlet.java</li>
										<li>DatabaseQueries.java</li>
										<ul>
											<li>We used the substring function to match movie titles and obtain a count of records. We read that substring is faster than LIKE in some cases, so in the future we could switch to using this function instead.</li>
					
										</ul>
									
									</ul>									
									
								</ul>
								
						</div>



						</div>

		

						
			    	</div>
				</div>
			</div>
		</div>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

	</body>
</html>
