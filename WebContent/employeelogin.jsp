<%
if (session.getAttribute("authenticated") == null) {
     
} else if(session.getAttribute("authenticated").equals("true")) {
	request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request,response);
}
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	    <link href="${pageContext.request.contextPath}/CSS/narrow-jumbotron.css" rel="stylesheet">
		<title>Fabflix</title>
	</head>
	<body>
		<div class="container">
			<%@ include file="navbar.jsp" %>
			<div class="jumbotron">
		     	<p class="lead">Please login to continue.</p>
		     	<div class="row">
		     	<div class="col-3"></div> 
		     	<div class="col-6">
						<div class="form-group">
							<input type="text" required class="form-control" id="username" name="username">
				  		</div>
				  		
				  		<div class="form-group">
				    		<input type="password" required class="form-control" id="password" name="password">
				  		</div>
				  		
				  		<p class="lead">${message}</p>
				  		<c:remove var="message" scope="session" />
				  		
				  		<div class="g-recaptcha" data-sitekey="6LcO6RUUAAAAAOJfYRwq3NpxD5niBX9mWzYfo9mF"></div>
				  		
				  		<br>

				  		<input type="submit" class="btn btn-info" value="Login">
				  		
				  		

					</form>

					
				</div>
				</div>
		    </div>
		</div>
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	</body>
</html>
