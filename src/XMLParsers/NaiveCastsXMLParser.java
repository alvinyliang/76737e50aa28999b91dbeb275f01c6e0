package XMLParsers;
import java.util.HashMap;
import java.sql.*;

/*
 * All are the same except that it doesn't use IDMaps to get movie_id and star_id; 
 * instead, it uses query to generate them.
 */
public class NaiveCastsXMLParser extends CastsXMLParser {
	
	public NaiveCastsXMLParser(){
		System.out.println("Naive CastsXMLParser");
	}
	
	public String getMID(String title){
		// System.out.println("NaiveCastsMID");
		return getId(title, 
				"select id from movies where title = \"%s\" ;"); 
	}
	
	public String getSID(String name){
		// System.out.println("NaiveCastsSID");
		return getId(name, 
				"select id from stars where "
				+ "CONCAT(stars.first_name ,\" \", stars.last_name) = \"%s\" ;");
	}
	
	
	private String getId(String title, String sqlFormat){
		// System.out.println("NaiveCasts");
		String id = "null";
		Connection conn = DBConnection.conn;
		try{
			
			
			ResultSet rst = conn.createStatement().executeQuery(
					String.format(sqlFormat, title));
			
			while(rst.next()){
				id = rst.getString("id");
			}
			
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}
		
		
		return id;
	}
}
