
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
							<h3 class="display-3">Employee Dashboard</h3>
					</div>
					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
										<c:if test="${not empty message}">
											<div class="alert alert-warning alert-dismissible fade show" role="alert">
											  
											  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
											    <span aria-hidden="true">&times;</span>
											  </button>
											  ${message}
											</div>		
										</c:if>
										<c:remove var="message" scope="session" />
							<div id="accordion" role="tablist" aria-multiselectable="true">
								<div class="card">
									<div class="card-header" role="tab" id="headingOne">
										<h5 class="mb-0"><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">View Table Metadata</a></h5>
									</div>
									
									<div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne">
										<div class="card-block">
											<c:forEach var="tableObject" items="${tables}">
												<h2>${tableObject.name}</h2>


												<table class ="table table-condensed">
													<thead>
														<tr>
															<th>Attribute</th>
															<th>Type</th>
														</tr>
													</thead>
													<tbody>
													<c:forEach var="entry" items="${tableObject.getColumns()}">
														<tr>
													    <td width="50%">${entry.key}</td>
													    <td width="50%">${entry.value}</td>
													    </tr>
													</c:forEach>
													</tbody>
												</table>
											</c:forEach>
										</div>
									</div>
							  </div>
							  <div class="card">
							    <div class="card-header" role="tab" id="headingTwo">
							      <h5 class="mb-0">
							        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
							          Add new Star
							        </a>
							      </h5>
							    </div>
							    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo">
							      <div class="card-block">
									<form action="/Fabflix/_dashboard/Actions" method="POST" id="addStar">
										<input type="hidden" name="action" value="addStar">
										<div class="form-group row">
											<label class="col-2 col-form-label">First Name</label>
											<div class="col-10">
												<input class="form-control" type="text" name="first_name">
											</div>
										</div>
										<div class="form-group row">
											<label class="col-2 col-form-label">Last Name</label>
											<div class="col-10">
												<input class="form-control" type="text" name="last_name" required>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-2 col-form-label">Date of Birth</label>
											<div class="col-10">
												<input class="form-control" type="date" name="dob" required>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-2 col-form-label">Photo Url</label>
											<div class="col-10">
												<input class="form-control" type="url" name="photo_url">
											</div>
										</div>
										<button id="addStar" class="btn btn-primary" onsubmit='return false;'>Add Star</button>
									</form>
							      </div>
							    </div>
							  </div>
							  <div class="card">
							    <div class="card-header" role="tab" id="headingThree">
							      <h5 class="mb-0">
							        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
							          Add new Movie
							        </a>
							      </h5>
							    </div>
							    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
							      <div class="card-block">
								      <form action="/Fabflix/_dashboard/Actions" method="POST" id="addMovie">
											<input type="hidden" name="action" value="addMovie">
											<h3>Movie Information</h3><br>
											<div class="form-group row">
												<label class="col-2 col-form-label">Title</label>
												<div class="col-10">
													<input class="form-control" type="text" name="title" required>
												</div>
											</div>
											<div class="form-group row">
												<label class="col-2 col-form-label">Year</label>
												<div class="col-10">
													<input class="form-control" type="number" name="year" required>
												</div>
											</div>
											<div class="form-group row">
												<label class="col-2 col-form-label">Director</label>
												<div class="col-10">
													<input class="form-control" type="text" name="director" required>
												</div>
											</div>											

											<div class="form-group row">
												<label class="col-2 col-form-label">Genre</label>
												<div class="col-10">
													<input class="form-control" type="text" name="genre" required>
												</div>
											</div>
											<br>
											<h3>Star Information</h3><br>
											<div class="form-group row">
												<label class="col-2 col-form-label">First Name</label>
												<div class="col-10">
													<input class="form-control" type="text" name="first_name" required>
												</div>
											</div>
											<div class="form-group row">
												<label class="col-2 col-form-label">Last Name</label>
												<div class="col-10">
													<input class="form-control" type="text" name="last_name" required>
												</div>
											</div>

							
											<button id="addMovie" class="btn btn-primary" onsubmit='return false;'>Add Movie</button>
									  </form>
							      </div>
							    </div>
							  </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	</body>
</html>