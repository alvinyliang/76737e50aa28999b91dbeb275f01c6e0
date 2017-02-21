package Fabflix.employee;

import java.util.HashMap;

public class Table {
	public String name;
	public HashMap<String, String> columns;
	
	public Table() {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashMap<String, String> getColumns() {
		return this.columns;
	}
}
