

function searchResult(movie_title, pageId){
	$.ajax({
        type: "GET",
		url:"./Search",
        data: {"movie_title" : movie_title,
        	"pageId": pageId},
        success:function(result){
    		location.reload();
    		$("#content").html(result);
    	}        
	});
}

function visitPage(pageId){
	$.ajax({
        type: "GET",
		url:"./Search",
        data: {"pageId": pageId},
        success:function(result){
    		location.reload();
    		$("#content").html(result);

    	}        
	});
}

$(document).ready(function () {

	  $("#search_movie_title").on("keyup", function(e) {
		    if(e.which == 13) {
		        searchResult($('input[name="movie_title"]').val(), 1);
		    }
		});
	  
	  $("#search_movie_button").on("click", searchResult);
	  
	  
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