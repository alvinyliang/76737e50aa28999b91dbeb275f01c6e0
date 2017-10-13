package Fabflix.employee;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Fabflix.DBConnection;

public class EmployeeServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
	        HttpSession session = request.getSession(false);  
	        if (session.getAttribute("employeeAuthenticated") == null) {  
	    		request.getRequestDispatcher("employeelogin.jsp").forward(request,response);
	    		return;
	        }
	        else {
        		request.setAttribute("tables", getDataBaseMetaData());
	     		request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
	     		return;
	        }  
    	} catch (Exception e) {
    		
    	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request, response);
    }
    
    private ArrayList<Table> getDataBaseMetaData() {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        ArrayList<Table> tables = new ArrayList<Table>();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn = dbConn.getConnection();
        	//conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	stmt = conn.prepareStatement("show tables;");
        	ResultSet rs = stmt.executeQuery();
        	
        	while (rs.next()) {
        		Table table = new Table();
        		table.name = rs.getString("Tables_in_moviedb");
        		table.columns = getTableMetaData(table.name);
        		tables.add(table);
        	}
        	
        } catch (SQLException se) { 
        	se.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return tables;
    }
    
    private HashMap<String, String> getTableMetaData(String tableName) {
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/db_config.properties");
        DBConnection dbConn = new DBConnection(input);
        Connection conn;
        PreparedStatement stmt;
        HashMap<String, String> columns = new HashMap<String, String>();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn = dbConn.getConnection();
        	//conn = DriverManager.getConnection(dbConn.DB_URL, dbConn.DB_USERNAME, dbConn.DB_PASSWORD);
        	stmt = conn.prepareStatement("SELECT column_name, column_type FROM INFORMATION_SCHEMA.columns where table_name = ?");
        	stmt.setString(1, tableName);
        	ResultSet rs = stmt.executeQuery();
        
        	while (rs.next()) {
        		String columnName = rs.getString("column_name");
        		String columnType = rs.getString("column_type");
        		columns.put(columnName, columnType);
        	}

        } catch (SQLException se) { 
        	se.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return columns;
    }
}
