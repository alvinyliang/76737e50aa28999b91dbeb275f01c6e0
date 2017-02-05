package Fabflix;

import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
	public int id;
	public String title;
	public String director;
	public int year;
	public String banner;
	public String trailer;
	public String backdrop;
	public ArrayList<Star> stars;
	public HashMap<Integer, String> genres;
	
	public Movie() {
		
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public ArrayList<Star> getStars() {
		return this.stars;
	}
	
	public HashMap<Integer, String> getGenres() {
		return this.genres;
	}
	
	public String getDirector() {
		return this.director;
	}
	
	public String getTrailer() {
		return this.trailer;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getBackdrop() {
		return this.backdrop;
	}
	
	public String getBanner() {
		return this.banner;
	}
}
