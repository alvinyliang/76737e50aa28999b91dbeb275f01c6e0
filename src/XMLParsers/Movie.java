package XMLParsers;


import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
	public int dbId;
	public String title;
	public String director;
	public String year;
	public ArrayList<String> stars = new ArrayList<String>();
	public ArrayList<String> genres = new ArrayList<String>(); // catagory codes
	
	public Movie() {}
	
	public String getTitle() {return this.title;}
	
	public String getYear() {return this.year;}
	
	public ArrayList<String> getStars() {return this.stars;}
	
	public ArrayList<String> getGenres() {return this.genres;}
	
	public String getDirector() {return this.director;	}
	
	public int getDBId() {return this.dbId;}
	
	
	public void setTitle(String title){this.title = title;}
	
	public void setYear(String year){this.year = year;}

	public void setDirector(String director){this.director = director;}
	
	public void addStars(String star){stars.add(star);}
	
	public void addGenres(String genre){genres.add(genre);}
	
	public String toString(){
		String result = "Movie{"+
						"\tTitle: " + title + ";\n" +
						"\tYear:  " + year  + ";\n" +
						"\tDirct: " + director + ";\n" +
						"\tStars: " + stars + ";\n" +
						"\tGenre: " + genres+ ";\n}";
		return result;
	}
	
}
