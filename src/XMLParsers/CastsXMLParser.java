package XMLParsers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;




public class CastsXMLParser extends DefaultHandler{
	
	static public HashMap<String, HashSet<String>> existingPair = new HashMap<String, HashSet<String>>();
					// movie id : set (star id)
	static public int groupSize = XMLParser.groupSize;
	
	
	public ArrayList<String> queries = new ArrayList<String>(); 
	public HashMap<String, String> currentMap = new HashMap<String, String>();
	public String currentChar = "";
		
	
	
	
	public void parseDocument(String file) {

		// start
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
        	System.out.println("=========Parsing casts=========");
        	genExistingPair();
        	currentMap.put("movie", "null");
        	currentMap.put("star", "null");
        	
            SAXParser sp = spf.newSAXParser();
            sp.parse(file, this);
            DBConnection.execute(queries);
            System.out.println("=========Added all stars_in_movies=========");

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("m")){
			currentMap.put("movie", "null");
			currentMap.put("star", "null");
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentChar = new String(ch, start, length);
		currentChar = currentChar.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
	}
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("t")){
			currentMap.put("movie", currentChar);
		}else if (qName.equalsIgnoreCase("a")){
			currentMap.put("star", currentChar);
		}else if (qName.equalsIgnoreCase("m")){
			
			addQuery(currentMap);
			if (queries.size() >= groupSize){
				DBConnection.execute(queries);
			}
		}
		
	}

	
	private void addQuery(HashMap<String, String> cMap){
		
		String mid = getMID(cMap.get("movie"));
		String sid = getSID(cMap.get("star"));
		
		
		
		if (existingPair.containsKey(mid) && existingPair.get(mid).contains(sid)){
//			System.out.println("Duplicate: " + cMap.get("movie") + " | " + cMap.get("star"));
			return;
		}
		
		if (mid.equalsIgnoreCase("null") || 
				sid.equalsIgnoreCase("null")){
			return;
		}
		
		String mFormat = "insert into stars_in_movies value(%s, %s);";
		queries.add(String.format(mFormat, sid, mid));
//		System.out.println(String.format(mFormat, cMap.get("movie"), cMap.get("star")));
		
		
	}
	
	private void genExistingPair(){
		Connection conn = DBConnection.conn;
		try{
			String sql = "select movie_id, star_id from stars_in_movies;";
    		ResultSet rst = conn.createStatement().executeQuery(sql);
			while(rst.next()){
				String mid = rst.getString("movie_id");
				String sid = rst.getString("star_id");
				if (! existingPair.containsKey(mid)){
					existingPair.put(mid, new HashSet<String>());
					
				}
				existingPair.get(mid).add(sid);
				
			}
			
			
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public String getMID(String title){
		return IDMaps.getMovieID(title); // get movie id 
	}
	
	public String getSID(String name){
		return IDMaps.getStarID(name);
	}
	
}
