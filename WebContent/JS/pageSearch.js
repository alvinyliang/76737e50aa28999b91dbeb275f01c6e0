$(document).ready(function () {

	  $("#search_movie_title").on("keyup", function(e) {
		  if(e.which == 13) {
			title = $("#search_movie_title").val();
			
			
			$.ajax({
			        type: "POST",
			        url: "Search.jsp",
			        data: { movie_title : title }, // or the string: 'id=1'
			        complete:
			        function () {
			            window.location = "Search.jsp";
			        }

			});
			
		  }
	  	});	

	  
	  $("#search_movie_button").on("click", function(){
				title = $("#search_movie_title").val();
				var redirectUrl = 'Search.jsp';
				
				var form = $('<form action="' + redirectUrl + '" method="post">' +
						'<input type="hidden" name="movie_title" value="' + title +  '" />' +
						'<input type="hidden" name="parameter2" value="Sample data 2" />' +
						'</form>');
				$('#content').append(form);
						
				$(form).submit(
						
				);

	  });
	  
	  $('.btn-group').hover(function() {
		  $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(500);
		}, function() {
		  $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(500);
		  
		});
	  
	  

	  
	});