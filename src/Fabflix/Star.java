package Fabflix;

public class Star {
	public int id;
	public String firstName;
	public String lastName;
	public String dob;
	public String photo;
	
	public Star() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getDob() {
		return this.dob;
	}
	
	public String getPhoto () {
		return this.photo;
	}
}
