      <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills float-right">
            <li class="nav-item">
              <a class="nav-link active" href="Home"><i class="fa fa-home" aria-hidden="true"></i> Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">About</a>
            </li>
            <% if (session.getAttribute("authenticated") != null) {
                %><li class="nav-item">
                <a class="nav-link" href="Cart"><i class="fa fa-shopping-cart"></i> View Cart</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="Logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a>
              </li>
           <%}%>
          </ul>
        </nav>
        <h3 class="text-muted">Fabflix</h3>
      </div>