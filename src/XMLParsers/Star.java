package XMLParsers;


import java.util.ArrayList;

public class Star {
	public int id;
	public String firstName;
	public String lastName;
	public String dob;
		
	public Star() {
		
	}
	
	public int getId() { return this.id; }
	
	public String getName() { return this.firstName + " " + this.lastName; }
	public String getFirstName() { return this.firstName; }
	public String getLastName() { return this.lastName; }
	
	public String getDob() { return this.dob; }
	
	
	
	public void setDob(String dob) {this.dob = dob;}
	
	public void setName(String name){
		ArrayList<String> splited = new ArrayList<String>();
		for (String part: name.split(" ")){
			splited.add(part);
		}
		
		lastName = splited.remove(splited.size()-1);
		firstName = "";
		for (String part: splited){
			firstName += part + " ";
		}
		
		firstName = firstName.replaceAll("^ +", "").replaceAll(" +$", "");
		
		
	}
	
	
}
