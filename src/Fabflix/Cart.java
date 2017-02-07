package Fabflix;

import java.util.HashMap;

public class Cart {
	public static HashMap<String, Integer> cart = new HashMap<String, Integer>();
	public static HashMap<String, Movie> movies = new HashMap<String, Movie> ();
	
	public Cart() {
	}
	
	public static boolean removeFromCart(String movieId) {
		if (cart.containsKey(movieId)) {
			cart.remove(movieId);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean updateCart(String movieId, int quantity) {
		if (quantity < 0) {
			return false;
		}
		if (quantity == 0) {
			removeFromCart(movieId);
			return true;
		} else {
			cart.put(movieId, quantity);
		}
		return true;
	}
	
	public static void addToCart(String movieId, Movie movie) {
		int count = cart.containsKey(movieId) ? cart.get(movieId) : 0;
		
		if (count == 0) {
			cart.put(movieId, 1);
		} else {
			cart.put(movieId, cart.get(movieId) + 1);
		}
		movies.put(movieId, movie);
	}
	
	public HashMap<String, Integer> getCart() {
		return this.cart;
	}
	
	public static void cleanCart(){ 
		cart = new HashMap<String, Integer>();
		movies = new HashMap<String, Movie> ();
		
	}
}
