package Fabflix;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;


public class DatabaseQueries {
	Connection conn = null;
	
	public DatabaseQueries(Connection conn) {	
		this.conn = conn;
	}
	
	private String checkBanner(String bannerUrl) {
    	try {
        	URL url = new URL(bannerUrl);
        	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        	huc.setRequestMethod("HEAD");
        	int responseCode = huc.getResponseCode();

        	if (responseCode != 200) {
        		return "https://i.imgur.com/OZISao4.png";
        	}
        	else {
        		return bannerUrl;
        	}
    	} catch (Exception e) {
    		return "https://i.imgur.com/OZISao4.png";
    	}
	}
	
	public int getTotalTitleRows(String titleChar) {
		int totalRows = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement("select count(*) from movies where substring(movies.title from 1 for 1) = ?");
			stmt.setString(1, titleChar);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				totalRows = rs.getInt(1);
			} 
		} catch (Exception e) {
			
		}
		return totalRows;
		
	}
	
	public Boolean checkCreditCard(String cardNumber, String first_name, String last_name, java.util.Date exp) {
		try {
			PreparedStatement stmt = conn.prepareStatement("select count(distinct creditcards.id) as count from creditcards where id = ? and first_name = ? and last_name = ? and expiration = ?");
			stmt.setString(1, cardNumber);
			stmt.setString(2, first_name);
			stmt.setString(3, last_name);
			java.sql.Date sqlDob = new java.sql.Date(exp.getTime());
			stmt.setDate(4, sqlDob);
			
			ResultSet rs=stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("count") > 0)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public void saveOrder(String userId, HashMap<String, Integer> cart) {
		for (HashMap.Entry<String, Integer> entry : cart.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				try {
					PreparedStatement stmt = conn.prepareStatement("Insert into sales (customer_id, movie_id, sale_date) VALUES(?, ?, CURDATE());");
					stmt.setString(1, userId);
					stmt.setString(2, entry.getKey());
					stmt.executeQuery();
				} catch (SQLException e) {

				}
				
			}
		}
	}
	public int getTotalGenreRows(String genreName) {
		//Rewrite query to use genreId?
		int totalRows = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM genres_in_movies join movies join genres on genres_in_movies.movie_id = movies.id and genres_in_movies.genre_id = genres.id WHERE name = ?");
			stmt.setString(1, genreName);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				totalRows = rs.getInt(1);
			}
		} catch (Exception e) {
			
		}
		return totalRows;	
	}
	
	public HashMap<Integer, String> getStarMovies(int starId) {
        PreparedStatement stmt;
        HashMap<Integer, String> movies = new HashMap<Integer, String> ();
        
        try {
    	    stmt = conn.prepareStatement("select * from stars_in_movies join movies on stars_in_movies.movie_id = movies.id where star_id = ?");
    	    stmt.setInt(1, starId);
    
    	    ResultSet rs = stmt.executeQuery();
	
	        while (rs.next()){
	        	int movieId = rs.getInt("movie_id");
	        	String title = rs.getString("title");
	        	movies.put(movieId, title);
	        }
        } catch (Exception e) {
        	
        }
    	return movies;	
	}
	
	public Star getStarDetails(int starId) {
        PreparedStatement stmt;
        Star star = new Star();
        
        try {
    	    stmt = conn.prepareStatement("select * from stars where id = ?");
    	    stmt.setInt(1, starId);
    
    	    ResultSet rs = stmt.executeQuery();
	
	        while (rs.next()){
	        	star.id = rs.getInt("id");
	        	star.firstName = rs.getString("first_name");
	        	star.lastName = rs.getString("last_name");
	        	star.dob = rs.getString("dob");
	        	star.photo = rs.getString("photo_url");
	        	star.movies = getStarMovies(starId);

	        }
        } catch (Exception e) {
        	
        }
    	return star;		
	}
	
    public ArrayList<Star> queryStars(int movieId) {
        PreparedStatement stmt;
        ArrayList<Star> starList = new ArrayList<Star>();
        try {
    	    stmt = conn.prepareStatement("SELECT * FROM stars_in_movies join stars ON stars_in_movies.star_id = stars.id WHERE movie_id = ?");
    	    stmt.setInt(1, movieId);
    
    	    ResultSet rs = stmt.executeQuery();
	
	        while (rs.next()){
	        	Star star = new Star();
	        	star.id = rs.getInt(1);
	        	star.firstName = rs.getString(4);
	        	star.lastName = rs.getString(5);
	        	star.dob = rs.getString(6);
	        	star.photo = rs.getString(7);
	        	starList.add(star);
	        }
        } catch (Exception e) {
        	
        }
    	return starList;
    }
    
    public HashMap<Integer, String> queryGenres(int movieId){
        PreparedStatement stmt;
        HashMap<Integer, String> genres = new HashMap<Integer, String> ();
        try{
	        stmt = conn.prepareStatement("SELECT name, genre_id FROM movies join genres_in_movies join genres on movies.id = genres_in_movies.movie_id and genres.id = genres_in_movies.genre_id where movie_id = ? ;");
	        stmt.setInt(1, movieId);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	int genreId = rs.getInt("genre_id");
	        	String name = rs.getString("name");
	        	genres.put(genreId, name);
	        }
        } catch (Exception e){
        	
        }
        return genres;    	
    }
    
    

    
    public Movie getMovieDetails(int movieId) {
        PreparedStatement stmt;
    	Movie movie = new Movie();
        try{
            stmt = conn.prepareStatement("select * from movies where id= ?;");
            stmt.setInt(1, movieId);
         
        	ResultSet rs = stmt.executeQuery();

	        while (rs.next()){
//	        	movie.banner = checkBanner(rs.getString("banner_url"));
	        	movie.banner = rs.getString("banner_url");

	        	movie.id = rs.getInt("id");
	        	movie.title = rs.getString("title");
	        	movie.year = rs.getInt("year");
	        	movie.director = rs.getString("director");
	        	movie.trailer = rs.getString("trailer_url");
	        	movie.stars = queryStars(movie.id);

	        }
//	        stmt =  conn.prepareStatement("SELECT distinct backdrop_url from backdrops join movies on backdrops.movie_id = movies.id where movie_id = ?");
//            stmt.setInt(1, movieId);
//        	rs = stmt.executeQuery();
//            while (rs.next()) {
//            	movie.backdrop = rs.getString("backdrop_url");
//            }
            
            stmt = conn.prepareStatement("SELECT name, genre_id FROM movies join genres_in_movies join genres on movies.id = genres_in_movies.movie_id and genres.id = genres_in_movies.genre_id where movie_id = ? ;");
            stmt.setInt(1, movieId);
            rs = stmt.executeQuery();
            
            HashMap<Integer, String> genres = new HashMap<Integer, String> ();
            while (rs.next()) {
            	int genreId = rs.getInt("genre_id");
            	String name = rs.getString("name");
            	genres.put(genreId, name);
            }
            movie.genres = genres;
			return movie;
	    } catch (Exception e) {
	    	
	    }
        return movie;
    }
    
    public Movie getSimpleMovieDetails(String movieId) {
        PreparedStatement stmt;
    	Movie movie = new Movie();
        try	{
            stmt = conn.prepareStatement("select * from movies where id= ?;");
            stmt.setString(1, movieId);
         
        	ResultSet rs = stmt.executeQuery();

	        while (rs.next()){
	        	movie.banner = rs.getString("banner_url");
	        	movie.id = rs.getInt("id");
	        	movie.title = rs.getString("title");
	        	movie.year = rs.getInt("year");
	        	movie.director = rs.getString("director");
	        	movie.trailer = rs.getString("trailer_url");
	        }
	        
        } catch (Exception e) {
        	
        }
        return movie;
    }
	
}
