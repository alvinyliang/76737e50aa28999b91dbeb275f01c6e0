package XMLParsers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;




public class MovieXMLParser extends DefaultHandler{
	
	static public HashSet<String> existingMovieList = new HashSet<String>();
	static public int groupSize = XMLParser.groupSize;
	
	
	public ArrayList<String> queries = new ArrayList<String>();
	
	public MovieInCat mic = new MovieInCat(); 
	public String currentDirector = "";
	public Movie currentMovie = new Movie();
	public String currentChar = "";
	
	
	
	public void parseDocument(String file) {

		// start
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
        	System.out.println("=========Parsing main=========");
        	genExistingMovieList();
        	
            SAXParser sp = spf.newSAXParser();
            sp.parse(file, this);
            DBConnection.execute(queries);
            IDMaps.genMovieMap();
            System.out.println("=========Added all movies=========");
            
            System.out.println("=========Adding genres=========");
            mic.generate();
            System.out.println("=========Added all genres=========");
            
            System.out.println("=========Adding all genres_in_movies=========");
            mic.Update();
            System.out.println("=========Added all genres_in_movies=========");

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// clean up if needed
		if (qName.equalsIgnoreCase("director")){
			currentDirector = "";
		}
		else if (qName.equalsIgnoreCase("film")){
			currentMovie = new Movie();
		
		}
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		// save the value into currentChar
		currentChar = new String(ch, start, length);
		currentChar = currentChar.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
	}
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// update
		
		if (qName.equalsIgnoreCase("film")){
			
			addQuery(currentMovie);
			if (queries.size() >= groupSize){
				DBConnection.execute(queries);
			}
			
		}else if (qName.equalsIgnoreCase("t")){
			currentMovie.setTitle(currentChar);
			
		}else if (qName.equalsIgnoreCase("year")){

			if (currentChar.matches("^[0-9]{4}$")){
				currentMovie.setYear("\""+ currentChar+"\"");
			}
			else{
				currentMovie.setYear("0000");
			}
			
		}else if (qName.equalsIgnoreCase("dirn")){
			if (currentDirector.isEmpty()){
				currentDirector = currentChar;
				
			}
			currentMovie.setDirector(currentDirector);
			
		}else if (qName.equalsIgnoreCase("cat")){
			currentMovie.addGenres(currentChar);
			
		}else if (qName.equalsIgnoreCase("dirname")){
			currentDirector = currentChar;
		}
		
	}
	
	
	private void addQuery(Movie cMovie){
		// generate queries according to the Movie, 
		// include insert movies and corresponding insert genres_in_movies
		// if failed, execute one query at a time until the error is spotted 
		// System.out.println(cMovie.toString());
		
		// if the movie is already in the db, don't insert
		if (hasDuplicate(cMovie)){
			// System.out.println("Duplicate: " + cMovie.getTitle());
			return; 			
		}
		

		
		
		
		// insert into movies value (null, title, year, director, null, null);
		String mFormat = "insert into movies value(null, \"%s\", %s, \"%s\", null, null);";
		queries.add(String.format(mFormat, cMovie.getTitle(), cMovie.getYear(),
					cMovie.getDirector()));
		
		for (String genre: cMovie.getGenres()){
			 mic.insert(cMovie.getTitle(), genre);
			 
		}
		
		
		
	}
	
	private void genExistingMovieList(){
		Connection conn = DBConnection.conn;
		try{
			String sql = "select title from movies;";
    		ResultSet titles = conn.createStatement().executeQuery(sql);
			while(titles.next()){
				existingMovieList.add(titles.getString("title").toLowerCase());
			}
			
			
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		

	}
	
	
	
	
	
	
	public boolean hasDuplicate(Movie cMovie){
		return existingMovieList.contains(cMovie.getTitle().toLowerCase());
	}
	

}
