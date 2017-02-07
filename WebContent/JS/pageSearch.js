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
	  
	  
	  $("#browse_movie_button").click(function (){
		  $("#content").html("<p></p>")
		  $("#browse_index").attr("hidden", false);
	  });
	  
	  
	  $("[id^='./Browse']").click(function(){
		  
		  $.ajax({
                type: "GET",
				url: $ (this).attr('id'),
				data: {"order": "yd",
					   "p": 2, 
					   "m": 10},
				success: function(result){
		    		$("#content").html(result);
		    	}
			});
		  
		  
	  });
	  
	});

