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

					<div class="row pt-4">
						<div class="mx-auto" style="width: 1000px">
							<table id="cart" class="table table-hover table-condensed">
			    				<thead>
									<tr>
										<th style="width:50%">Product</th>
										<th style="width:10%">Price</th>
										<th style="width:8%">Quantity</th>
										<th style="width:22%" class="text-center">Subtotal</th>
										<th style="width:10%"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td data-th="Product">
											<div class="row">
												<div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..." class="img-responsive"/></div>
												<div class="col-sm-10">
													<h4 class="nomargin">Product 1</h4>
													<p>Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet.</p>
												</div>
											</div>
										</td>
										<td data-th="Price">$1.99</td>
										<td data-th="Quantity">
											<input type="number" class="form-control text-center" value="1">
										</td>
										<td data-th="Subtotal" class="text-center">1.99</td>
										<td class="actions" data-th="">
											<button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>
											<button class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></button>								
										</td>
									</tr>
								</tbody>
								<tfoot>
									<tr class="visible-xs">
										<td class="text-center"><strong>Total 1.99</strong></td>
									</tr>
									<tr>
										<td><a href="#" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
										<td colspan="2" class="hidden-xs"></td>
										<td class="hidden-xs text-center"><strong>Total $1.99</strong></td>
										<td><a href="#" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
									</tr>
								</tfoot>
						</table>
						<div class = "container ">
							
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
