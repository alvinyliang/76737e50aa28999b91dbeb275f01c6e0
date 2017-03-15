View the demo at https://zotflix.com

## When cloning the project:
  - Ensure MySQL Service is running on local machine.
  - Configure WebContent/WEB-INF/db_config.properties
  
  - Add External Jar -> MySQL Connector/J 6.0.5 to project buid path
  - Create /lib directory under /WEB-INF and include the following files
    - mysql-connector-java-6.0.5 https://mvnrepository.com/artifact/mysql/mysql-connector-java/6.0.5
    - jstl-1.2 https://mvnrepository.com/artifact/javax.servlet/jstl/1.2
    - javax.json 1.0.4 https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4
  - Under Eclipse "Problems" tab, change project JRE path to match current Java Version. Current JRE is set to version _111.
  - Set the database information in db_config.properties at WebContent/WEB-INF/db_config.properties
  
## Project Dependencies
  - Tomcat v8.5
  
## To do:
  - Implement connection pooling
  - Setup MySQL replication
  
  
## Completed:
  - Implemented search
  - Implemented substring matching and partial matching (SQL queries with LIKE and ILIKE)
  - Implemented browse
  - Implemented pagination for search and browse
  - Implemented movie and star info page
  - Implemented shopping cart and check out
  - Implemented order saving
  - Implemented login page and login post to servlet
  - Implemented Home page
  - Implemented authenciate user in session
  - Implemented session checking
    - If user is logged in, login page redirects to home page
  - Implement logout button (for session)
  - Implemented reCAPTHA
  - Implemented HTTPS
  - Implemented Employee Dashboard
  - Implemented stored procedures for inserting new movie/star
  - Implemented XML parser
  - Implemented Autocompletion Search
  - Implemented Auto popup window for each movie
  - Created Android App
  - Implemented Fuzzy search
  	- Based on edit distance
  	- Include three functions: 
  		- ed : integer ed(string s1, string s1):
  			- returns the edit distance between s1 and s2. 
  			- The function assumes that the strings have the same case.
		- edth : boolean edth(string s1, string s1, integer th)
			- returns true if the edit distance between s1 and s2 is smaller or equal than th. 
			- The function assumes that the strings have the same case.
			- This function might be slightly faster than ed as it can stop as soon as the edit distance goes above the threshold.
 		- edrec : boolean edrec(string s, string rec, integer th)
 			- returns true if there is a token in rec that has an edit distance from s smaller or equal to th. 
 			- The string in rec is tokenized using the following separators: " " (white space), "," (comma), and "." (dot).
 			- The function assumes that the query string is in lower case.
