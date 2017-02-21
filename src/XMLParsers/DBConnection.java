package XMLParsers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DBConnection {
	static String JDBC_DRIVER;
    static String DB_URL;
    static String dbUserName;
    static String dbPassword;
    static Connection conn = null;
    
        
    public static boolean executeUpdate(ArrayList<String> queries){
    	
    	int before = 0;
    	int after = 0;
    	
    	// select sum(table_rows) from information_schema.TABLES where table_schema = 'moviedb';
    	try{
    		String sumSQL = "select sum(table_rows) as sum "
    				+ "from information_schema.TABLES where table_schema = 'moviedb';";
    		ResultSet sum = conn.createStatement().executeQuery(sumSQL);
    		
    		while(sum.next()){
    			before = sum.getInt("sum");
    			after = before;
    		}
    		
    		
    		for (String query: queries){
	    		
		    	PreparedStatement stmt = conn.prepareStatement(query);
		    	stmt.executeUpdate();
	    	}
	    	
	    	conn.commit();
	    	
	    	sum = conn.createStatement().executeQuery(sumSQL);
    		
    		while(sum.next()){
    			after = sum.getInt("sum");
    		}
	    	
    	}catch (SQLException se){
//    		se.printStackTrace();
    		System.out.println(se.getMessage());
    		
    		
    	}
    	
    	
    	
    	return before != after;
    }
    
    public static boolean executeUpdate(String query){
    	
    	int before = 0;
    	int after = 0;
    	// select sum(table_rows) from information_schema.TABLES where table_schema = 'moviedb';
    	try{
    		String sumSQL = "select sum(table_rows) as sum "
    				+ "from information_schema.TABLES where table_schema = 'moviedb';";
    		ResultSet sum = conn.createStatement().executeQuery(sumSQL);
    		
    		while(sum.next()){
    			before = sum.getInt("sum");
    			after = before;
    		}
    		
    		
    		
	    	PreparedStatement stmt = conn.prepareStatement(query);
		    stmt.executeUpdate();
	    	
	    	conn.commit();
	    	
	    	sum = conn.createStatement().executeQuery(sumSQL);
    		
    		while(sum.next()){
    			after = sum.getInt("sum");
    		}
	    	
    	}catch (SQLException se){
//    		se.printStackTrace();
    		System.out.println(se.getMessage());
    	}
    	
    	
    	
    	return after != before;
    }
    
    static public void execute(ArrayList<String> queries){

		boolean result = DBConnection.executeUpdate(queries);
//		System.out.println("Successfully add " + result + " rows.");
		
		if (result){
			queries.clear();
			return;
		}
		
//		System.out.print("Result= ");
//		System.out.println(result);
		Iterator<String> itr = queries.iterator();
		System.out.println("Commit failed. Executing each row seperately...");
		while (itr.hasNext()){
			String sql = itr.next();
			result = DBConnection.executeUpdate(sql); 
			if (result){}
			else{
				System.out.println("\tUpdate failed. SQL: " + sql);
			}
			itr.remove();
		}
		System.out.println("... Done.");
		
	}

    static public void connectDB(InputStream input) {
        
        
        
    	try{
    		Properties properties = new Properties();
        	try {
    	    	properties.load(input);
    	    	DB_URL = properties.getProperty("jdbc.url");
    	    	dbUserName = properties.getProperty("jdbc.username");
    	    	dbPassword = properties.getProperty("jdbc.password");
        	} catch (IOException e) {
        		
        	}
    		
	        System.out.println("Connecting to database...");
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        	conn = DriverManager.getConnection(DB_URL, dbUserName, dbPassword);
        	conn.setAutoCommit(false);
        	System.out.println("Connected. \n");
        	
		}catch (SQLException se) {
//	            System.out.println("Unable to connect to database! Error Message: " + se.getMessage());
//	            se.printStackTrace();
		}catch (java.lang.Exception ex) {
    		
    	}
        
        
        
    }
    
    public static void disconnectDB(){
    	try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    
    

}
