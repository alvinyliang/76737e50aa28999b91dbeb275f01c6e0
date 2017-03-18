package Fabflix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.*;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;

public class DBConnection {
    public String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public String DB_URL;
    public String DB_USERNAME;
    public String DB_PASSWORD;
    
    public DBConnection(InputStream input) {
		Properties properties = new Properties();
    	try {
	    	properties.load(input);
	    	DB_URL = properties.getProperty("jdbc.url");
	    	DB_USERNAME = properties.getProperty("jdbc.username");
	    	DB_PASSWORD = properties.getProperty("jdbc.password");
    	} catch (IOException e) {
    		
    	}
    }
    
    public Connection getConnection() throws SQLException {
    	PoolProperties p = new PoolProperties();
    	p.setUrl(DB_URL);
    	p.setDriverClassName(JDBC_DRIVER);
    	p.setUsername(DB_USERNAME);
    	p.setPassword(DB_PASSWORD);
    	DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource( p );
    	datasource.setPoolProperties(p);
    	Connection conn = datasource.getConnection();
    	return conn;
    }
    
}
