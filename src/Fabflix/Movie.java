package Fabflix;

import java.util.ArrayList;

public class Movie {
	public int id;
	public String title;
	public String director;
	public int year;
	public String banner;
	public ArrayList<Star> stars;
	
	public Movie() {
		
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getYear() {
		return this.year;
	}
}
