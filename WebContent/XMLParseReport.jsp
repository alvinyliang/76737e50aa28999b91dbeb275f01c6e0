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
						<h3 class="display-3">XML Parsing Report</h3>
			<ul>
				<li>
					
						<h4>Notes: </h4>
					<ul>
					<li>
						mains243Test.xml, casts124Test.xml, actors63Test.xml are used for test.<br>
						By default, the program will parse mains243.xml, casts124.xml and actor63.xml. 
						To use *Test.xml, change the parameter in XMLParser.parse(...) in XMLParserServlet. <br>
						<br>
						For example, to parser mains243Test.xml instead of mains243.xml, <br>
						change															<br>
							" XMLParser.parse("mains243.xml", ...);"					<br>
						into 															<br>
							" XMLParser.parse("mains243Test.xml", ...);"				<br>
						
					</li>
					<li>
						The program is using fully optimized implementation. To use naive implementation instead, 
						toggle comment the corresponding lines in XMLParser.parse().	<br>
					</li>
					<li>
						
						All the test is run under moviedb that populated by data.sql provided in project 1.<br>
						All the test is run on the local MySQL server. It will be slower when running on the 
						website <br>
					
					</ul>
				</li>
				<li>
						<h4>Optimization 1:</h4>
						<br>
						<h5>Store existing tables (movies, genres, and stars) as maps to speed up the process 
						of fetching the id of given movie/genre/star</h5>
					<ul>
					<li>
						<p>
						After parsing main243.xml, IDMaps sends a query to database, asks for the title and 
						id of all the movies in database and stores them in a HashMap mapping each movie title 
						to its corresponding id. Thus, when updating genres_in_movies and stars_in_movies, the 
						movie_id can be obtained solely from IDMaps so that the interaction to the database 
						can be minimized.<br> 
						<br>
						Similar procedure also happens after parsing actrs63.xml. <br> 
						</p>
					</li>
					<li>
						<p>
						NaiveCastsXMLParse is a naive implementation of CastsXMLParse. All are the same except 
						that it doesn't use IDMaps to get movie_id and star_id; instead, it uses query to get 
						them from database.<br> 
						</p>
					</li>
					<li>
						<h5>Execution time without this optimization:</h5>
						<p>445.551 sec</p>
					</li>
					<li>
						<h5>Execution time with this optimization:</h5>
						<p>24.138 sec</p>
					</li>
					<li>
						<h5>Time reduced</h5>
						<p>421.413 sec (94.6%)</p>
					</li>
					</ul>
				</li>
				<li>
						<h4>Optimization 2:</h4>
						<br>
						<h5>Preload the existing tables to 
								speed up the process of checking duplicate.  </h5>
					<ul>
					<li>
						<p>
						Before parsing main243.xml, MovieXMLParser will generate a HashSet that stores all the 
						existing movie titles in the database so that they won't be included in update. Similar 
						procedure also happens before parsing actrs63.xml. 
						</p>
					</li>
					<li>
						<p>
						NaiveMovieParser and NaiveActorXMLParser are naive implementations of MovieParser and 
						ActorXMLParser. All are the same except that before adding each movie/actor, it uses 
						query to check with database in order to avoid duplicate movies/actors.<br> 
						</p>
					</li>
					<li>
						<h5>Execution time without this optimization:</h5>
						<p>75.374 sec</p>
					</li>
					<li>
						<h5>Execution time with this optimization:</h5>
						<p>24.138 sec</p>
					</li>
					<li>
						<h5>Time reduced</h5>
						<p>51.236 sec (68.0%)</p>
					</li>
					</ul>
				</li>
				<li>
						<h4>Optimization 3:</h4>
						<br>
						<h5>Group several transactions and commit them as one.</h5>
					<ul>
					<li>
						<p>
						All the queries for update are stored locally in an ArrayList and they are only 
						executed when the array reaches a certain size. Main.groupSize is the global 
						variable that defines the size of each group. In DBConnection.executeQueries, 
						AutoCommit is set to false, and the transactions won't be committed until all the 
						queries in the parameter are executed so that they can be seen as one. <br> 
						<br>
						Every time a query is added to a local ArrayList of queries, it will check if its 
						size is greater than or equal to groupSize. If so, they will be executed and the 
						ArrayList will be cleared.  <br> 
						</p>
					</li>
					<li>
						<p>
						Naive implementation is the same as groupSize = 1. Optimized implementation is 
						groupSize = 100<br> 
						</p>
					</li>
					<li>
						<h5>Execution time without this optimization:</h5>
						<p>589.31 sec</p>
					</li>
					<li>
						<h5>Execution time with this optimization:</h5>
						<p>24.138 sec</p>
					</li>
					<li>
						<h5>Time reduced</h5>
						<p>565.172 sec (95.9%)</p>
					</li>
					</ul>
				</li>
				<li>
					<h4>Total Time Reduction</h4>
					<ul>
					<li>
						<h5>Execution time using naive implementation (without any optimizations):</h5>
						<p>1112.86 sec</p>
					</li>
					<li>
						<h5>Execution time using optimized implementation (with all optimizations):</h5>
						<p>24.138 sec</p>
					</li>
					<li>
						<h5>Time reduced</h5>
						<p>1088.722 sec (97.8%)</p>
					</li>
					</ul>
				
				
			</ul>

					</div>

		

						
		    	</div>
			</div>
		</div>
	
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>

	</body>
</html>