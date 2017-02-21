package XMLParsers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.*;
import java.sql.*;
/*
* 	code  	category 	|
* 	Susp  	thriller |				// sus + thriller
*  	CnR   	cops and robbers |		// 
* 	Dram  	drama | 
* 	West 	western |
*   Myst 	mystery|
*   S.F.	science fiction|		//SCI 
*   Advt 	adventure|				
*   Horr 	horror | 
*   Romt 	romantic | 				
*   Comd 	comedy | 				 
*   Musc 	musical | 				
*   Docu 	documentary | 
*   Porn 	pornography, including soft | 
*   Noir 	black | 
*   BioP 	biographical Picture | 
*   TV 		TV show | 
*   TVs 	TV series | 
*   TVm 	TV miniseries | 
*   
*   
*/
/*
String pattern = "";
if (code.equalsIgnoreCase("S.F.")){
	pattern = "'%sci%'";
}else if (code.equalsIgnoreCase("Susp")){
	pattern = "'%susp%' or genres.name like '%thriller%'";
}else if (code.equalsIgnoreCase("Horr")){
	pattern = "'%" + code +"%'";
}else if (code.length() > 3){
	pattern = "'%" + code.substring(0, 3) + "%'";
}else{ pattern = "'%" + code +"%'";}
 */

// Map category code to genres


// TODO: rewrite
// TODO: clean up genre
public class CatToGenre {

	static public int groupSize = XMLParser.groupSize;
	// map cat code in xml to genres.id in moviedb
	// include all the different id with same name, 
	// e.g. Suspense and thriller, Sci/Fi and SciFi and Sci-Fi and Science Fiction
	
	
	
	static public HashMap<String, HashSet<String>> catCodeToName = new HashMap<String, HashSet<String>>(); 
	
	
	public HashSet<String> getGenreIds(String code){
		HashSet<String> result = new HashSet<String>();
		if(catCodeToName.containsKey(code)){
			for (String name: catCodeToName.get(code)){
				result.add(IDMaps.getGenreID(name));
			}
		}
		
		
		return result;
	}
	
	
	
	public void generate(HashSet<String> codeList){
		// get existing genre names and ids
		IDMaps.genGnrMap();
		
		while (!codeList.isEmpty()){
			// generate cat code to genre name, 
			// removed the one that mapped to an existing genre
			genCatCodeToName(codeList);
			
			// create missing genres
			Update(codeList);
			
			// update genGnrNameToId
			IDMaps.genGnrMap();
		}
	}
	
	
	
	private void genCatCodeToName(HashSet<String> codeList){
		// given a set of cat codes, generate catCodeToName
		Iterator<String> itr = codeList.iterator();
		
		while(itr.hasNext()){
			String code = itr.next();
			
			
			HashSet<String> result = matched(code);
			if (! result.isEmpty()){
				
				if (! catCodeToName.containsKey(code)){
					catCodeToName.put(code, result);
				}else{
					catCodeToName.get(code).addAll(result);
				}
				
				itr.remove();
			}
		}
		
		
	}
	
	
	private HashSet<String> matched(String code){
		// find matched genre name to a given cat code
		HashSet<String> result = new HashSet<String>();
		
		code = code.replaceAll("\\p{Punct}", " ");
		
		Set<String> genres = IDMaps.gnrNameToId.keySet();
		for (String genre: genres){
			String tempGenre = genre.replaceAll("\\p{Punct}", " ");
			if (tempGenre.matches("^.*"+code+".*$")){
				result.add(genre);
			}
		}
		
		
		return result;
		
	}
	
	
	

	
	private void Update(HashSet<String> codeList){
		// for every pair, generateSQL, execute when reaches groupSize
		if (codeList.isEmpty()){return;}
		
		ArrayList<String> queries = new ArrayList<String>();
		
		for(String code: codeList){ 
			String sqlFormat = "insert into genres values(null, \"%s\");";
			
			queries.add(String.format(sqlFormat, code));
			if (queries.size() >= groupSize){
				DBConnection.execute(queries);
			}
			
		}
		
		DBConnection.execute(queries);
		
	}
	
	
	
		
	
	
	

}
