package XMLParsers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;




public class ActorXMLParser extends DefaultHandler{
	
	static public HashSet<String> existingStarList = new HashSet<String>();
	static public int groupSize = XMLParser.groupSize;
	
	
	public ArrayList<String> queries = new ArrayList<String>(); 

	public Star currentStar = new Star();
	public String currentChar = "";
	
	
	
	
	public void parseDocument(String file) {

		// start
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
        	System.out.println("=========Parsing actor=========");
        	genExistingStarList();

            SAXParser sp = spf.newSAXParser();
            sp.parse(file, this);
            DBConnection.execute(queries);
            System.out.println("=========Added all stars=========");
            IDMaps.genStarMap();

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
		
		if (qName.equalsIgnoreCase("actor")){
			currentStar = new Star();
		
		}
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		// save the value into currentChar
		currentChar = new String(ch, start, length);
		currentChar = currentChar.replaceAll("^\\s+", "").replaceAll("\\s+$", "").replaceAll("\\.$", "");
	}
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// update
		if (qName.equalsIgnoreCase("actor")){
			
			addQuery(currentStar);
			if (queries.size() >= groupSize){
				DBConnection.execute(queries);
			}
			
		}else if (qName.equalsIgnoreCase("stagename")){
			
			currentChar = currentChar.replaceAll("\\\"", "\\\\\"");
			
			currentStar.setName(currentChar);
		}else if (qName.equalsIgnoreCase("dob")){
			if (currentChar.matches("^[0-9]{4}$")){
				currentStar.setDob("\"" +currentChar+ "-00-00\"");
			}
			else{
				currentStar.setDob("null");
			}
		}
		
	}
	
	
	private void addQuery(Star cStar){
		// generate queries according to the actor, 
		
		
		// if the star is already in the db, don't insert
		if (hasDuplicate(cStar)){
//			System.out.println("Duplicate: " + cStar.getName());
			return;
		}
		
		// insert into movies value (null, first_name, last_name, dob, null);
		String mFormat = "insert into stars value(null, \"%s\", \"%s\", %s, null);";
		queries.add(String.format(mFormat, cStar.getFirstName(), 
				cStar.getLastName(), cStar.getDob()));
		
		
		
		
	}
	
	private void genExistingStarList(){
		Connection conn = DBConnection.conn;
		try{
			String sql = "select first_name, last_name from stars;";
    		ResultSet rst = conn.createStatement().executeQuery(sql);
			while(rst.next()){
				existingStarList.add((rst.getString("first_Name") +
						" " + rst.getString("last_Name")).toLowerCase());
			}
			
			
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
		

	}
	
	
	
	

	public boolean hasDuplicate(Star cStar){
		return existingStarList.contains(cStar.getName().toLowerCase());
	}
}
