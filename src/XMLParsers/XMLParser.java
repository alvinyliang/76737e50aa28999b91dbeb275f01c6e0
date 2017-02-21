package XMLParsers;

import java.io.InputStream;

public class XMLParser {
	// ================Optimized=======================
	static final int groupSize = 100;
	// ================Naive=======================
//	static final int groupSize = 1;
	
	
	

	public static void parse(String mFile, String aFile, String cFile) {
		System.out.println("Hello Sir");
		// ===============Initial Connection==============
		// moved to XMLParser Servlet
		
		
		long tStart = System.currentTimeMillis();

		
		// ================Optimized=======================
		MovieXMLParser movieP = new MovieXMLParser();		
		ActorXMLParser actorP = new ActorXMLParser();
		CastsXMLParser castP = new CastsXMLParser();
		
		// =================Naive=========================
//		NaiveMovieXMLParser movieP = new NaiveMovieXMLParser();
//		NaiveActorXMLParser actorP = new NaiveActorXMLParser();
//		NaiveCastsXMLParser castP = new NaiveCastsXMLParser();
		
		
		
		
		
		// =================parse main=================
			// update	 movies, 
			// update genres, genres_in_movies
		movieP.parseDocument(mFile);

				
		// ===============parse actors================
			// update stars
		actorP.parseDocument(aFile);

		
		// ==============parse casts=================
			// populate stars_in_movies
		castP.parseDocument(cFile);
		
		
		
		// ===========Display elapsed time============== 
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;
		System.out.println("ElapsedSeconds = " + elapsedSeconds);
		
	}
}
