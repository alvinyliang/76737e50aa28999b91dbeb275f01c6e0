package Fabflix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    public String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public String DB_URL;
    public String DB_USERNAME;
    public String DB_PASSWORD;
    
    public DBConnection(InputStream input) {
		Properties properties = new Properties();
    	try {
	    	properties.load(input);
	    	DB_URL = properties.getProperty("url");
	    	DB_USERNAME = properties.getProperty("username");
	    	DB_PASSWORD = properties.getProperty("password");
    	} catch (IOException e) {
    		
    	}
    }
    
}
