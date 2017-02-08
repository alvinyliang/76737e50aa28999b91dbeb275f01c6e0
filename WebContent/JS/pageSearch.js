$(document).ready(function () {
		
	  $("#search_movie_title").on("keyup", function(e) {
		    if(e.which == 13) {
		        var title = $('input[name="movie_title"]').val();

		        var html = '';
		        
		        html += '<form action="Search.jsp" method="post" id="search_form">'
		        	+ '<input type="hidden" name="movie_title" id="search_movie_title" value="' + title + '">'
		        	+ '</form>';
		        
		        $('#content').html(html);
		        $('#search_form').submit();
		        
		        
		    }
		});
	  
	  $("#search_movie_button").on("click", function(){
		  
	        var title = $('input[name="movie_title"]').val();

	        var html = '';
	        
	        html += '<form action="Search.jsp" method="post" id="search_form">'
	        	+ '<input type="hidden" name="movie_title" id="search_movie_title" value="' + title + '">'
	        	+ '</form>';
	        
	        $('#content').html(html);
	        $('#search_form').submit();		  
		  
		  
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
		  $(this).find('.dropdown-menu').stop(true, true).fadeIn(500);
		}, function() {
		  $(this).find('.dropdown-menu').stop(true, true).fadeOut(500);
		  
		});
	  
	  

	  
	});

