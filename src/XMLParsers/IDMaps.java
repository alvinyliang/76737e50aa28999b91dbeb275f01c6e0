package XMLParsers;
import java.util.HashMap;
import java.sql.*;

public class IDMaps {
	static public HashMap<String, String> starNameToId = new HashMap<String, String>();
	static public HashMap<String, String> movTitleToId = new HashMap<String, String>();
	static public HashMap<String, String> gnrNameToId = new HashMap<String, String>();
	
	static public void generateAll(){
		
		
		genStarMap();
		genMovieMap();
		genGnrMap();
		
		
	}
	
	static public String getMovieID(String title){
		if (! movTitleToId.containsKey(title.toLowerCase())){
			return "null";
		}
		return movTitleToId.get(title.toLowerCase());
	}
	
	static public String getStarID(String name){
		if (! starNameToId.containsKey(name.toLowerCase())){
			return "null";
		}
		return starNameToId.get(name.toLowerCase());
	}
	
	static public String getGenreID(String name){
		if (! gnrNameToId.containsKey(name.toLowerCase())){
			return "null";
		}
		return gnrNameToId.get(name.toLowerCase());
	}
	
	static public void genStarMap(){
		Connection conn = DBConnection.conn;
		String sql = "select CONCAT(stars.first_name, \" \", stars.last_name) as name, "
				+ "stars.id from stars;";
		try{
			ResultSet rst = conn.createStatement().executeQuery(sql);
			while(rst.next()){
				starNameToId.put(rst.getString("name").toLowerCase(), rst.getString("id"));
			}
			
			
			
		}catch (SQLException se){
			System.out.println(se.getMessage());
		}
		
		
	}
	
	static public void genMovieMap(){
		Connection conn = DBConnection.conn;
		String sql = "select id, title from movies;";
		try{
			ResultSet rst = conn.createStatement().executeQuery(sql);
			while(rst.next()){
				movTitleToId.put(rst.getString("title").toLowerCase(), rst.getString("id"));
			}
			
			
			
		}catch (SQLException se){
			System.out.println(se.getMessage());
		}
		
		
		
	}
	
	
	static public void genGnrMap(){
		Connection conn = DBConnection.conn;
		
		
		String sql = "select name, id from genres;";
		try{
			ResultSet rst = conn.createStatement().executeQuery(sql);
			while(rst.next()){
				gnrNameToId.put(rst.getString("name").toLowerCase(), rst.getString("id"));
			}
			
			
			
		}catch (SQLException se){
			System.out.println(se.getMessage());
		}
				
		
	}
	
	
}
