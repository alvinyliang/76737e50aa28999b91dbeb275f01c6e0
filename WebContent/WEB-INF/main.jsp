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
						            <input type="search" placeholder="Search by title" class="form-control">
		         				</div>
		         				<div class="pt-2">
			         				<div class="btn-group">
										<button a href="Search" type="button" class="btn btn-info">Search</button>
										<button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu">
											<a a href="/Search/Advanced" class="dropdown-item" href="#">Advanced Search</a>
										</div>
									</div>
			         				<div class="btn-group">
										<button a href="Browse" type="button" class="btn btn-info">Browse</button>
										<button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu">
											<a a href="Browse/Title" class="dropdown-item" href="#">By Title</a>
											<a a href="Browse/Genre" class="dropdown-item" href="#">By Genre</a>
										</div>
									</div>									
								</div>
							</div>
						</div>
						<div id="content"></div>
			    	</div>
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	</body>
</html>
