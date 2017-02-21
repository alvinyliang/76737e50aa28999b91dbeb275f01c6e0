package XMLParsers;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * All are the same except that before adding each movie/actor, 
 * it uses query to check with database in order to avoid duplicate movies/actors.
 */
public class NaiveActorXMLParser extends ActorXMLParser {

	public NaiveActorXMLParser(){
		System.out.println("Naive ActorXMLParser");
	}
	
	public boolean hasDuplicate(Star cStar){
		// System.out.println("NaiveActorHasDuplicate");
//		System.out.println(cStar.getName());
		boolean result = false;
		Connection conn = DBConnection.conn;
		try{
			String sqlFommat = "select count(id) as c from stars "
					+ "where CONCAT(stars.first_name ,\" \", stars.last_name) = \"%s\" ;";
			ResultSet rst = conn.createStatement().executeQuery(
				String.format(sqlFommat, cStar.getName()));
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
