package XMLParsers;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

 
// TODO: rewrite 
public class MovieInCat {
	static public int groupSize = XMLParser.groupSize;


	
	
	
	public ArrayList<String> queries = new ArrayList<String>();
	public HashSet<String> codeList = new HashSet<String>();
	public HashMap<String, HashSet<String>> movieToCode = new HashMap<String, HashSet<String>>();
	public HashMap<String, HashSet<String>> movIdToGnrId = new HashMap<String, HashSet<String>>();
	public CatToGenre ctg = new CatToGenre();
	
	
	public void insert(String movieTitle, String code){
		movieTitle = movieTitle.toLowerCase();
		code = code.toLowerCase();
		if(! movieToCode.containsKey(movieTitle)){
			movieToCode.put(movieTitle, new HashSet<String>());

		}
		codeList.add(code);
		
		movieToCode.get(movieTitle).add(code);
		
	}
	
	public void generate(){
		
		ctg.generate(codeList);
		
//		System.out.println(ctg.catCodeToName);
		for (Entry<String, HashSet<String>> mtc: movieToCode.entrySet()){
			String title = mtc.getKey();
			String mid = IDMaps.getMovieID(title);
			if(! movIdToGnrId.containsKey(mid)){
				movIdToGnrId.put(mid, new HashSet<String>());
			}
			
			for (String code : mtc.getValue()){
				movIdToGnrId.get(mid).addAll(ctg.getGenreIds(code));
			}
			
		}
	}
	
	
	
	
	public void Update(){
		// for every pair, generateSQL, execute when reaches groupSize
//		System.out.println(movIdToGnrId);
		for (Entry<String, HashSet<String>> mtg: movIdToGnrId.entrySet()){
			String mid = mtg.getKey();
			for (String gid: mtg.getValue()){
				if (gid.equalsIgnoreCase("null") 
						|| mid.equalsIgnoreCase("null")){
					continue;
				}
				queries.add(generateSQL(gid, mid));
				if (queries.size() >= groupSize){
					DBConnection.execute(queries);
				}
			}
			
		}
		DBConnection.execute(queries);
	}
	
	
	
	private String generateSQL(String gid, String mid){
		String sql = "insert into genres_in_movies values(" +
					gid + ", " + mid + ");" ;

		
		return sql;
	}
	
	

	
	
}
