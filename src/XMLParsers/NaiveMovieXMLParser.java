package XMLParsers;
import java.sql.*;
/*
 * All are the same except that before adding each movie/actor, 
 * it uses query to check with database in order to avoid duplicate movies/actors.
 */

public class NaiveMovieXMLParser extends MovieXMLParser {
	
	public NaiveMovieXMLParser(){
		System.out.println("Naive MovieXMLParser");
	}
		
	
	public boolean hasDuplicate(Movie cMovie){
		boolean result = false;
		// System.out.println("NaiveMovieHasDuplicate");
		Connection conn = DBConnection.conn;
		try{
			String sqlFommat = "select count(id) as c from movies where title = \"%s\";";
			ResultSet rst = conn.createStatement().executeQuery(
				String.format(sqlFommat, cMovie.getTitle()));
			while (rst.next()){
				if (rst.getInt("c") > 0)
					result = true;
			}
			
			
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
			
		}
		
		
		
		
		return result;
	}
}
