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
  -Set the database information in db_config.properties at WebContent/WEB-INF/db_config.properties
  
## Project Dependencies
  - Tomcat v8.5
  
## To do:
  
  
## Completed:
  - Implement search
  - Implement substring matching and partial matching (SQL queries with LIKE and ILIKE)
  - Implement browse
  - Implement pagination for search and browse
  - Implement movie and star info page
  - Implement shopping cart and check out
  - Implement order saving
  - Implemented login page and login post to servlet
  - Implemented Home page
  - Implemented authenciate user in session
  - Implemented session checking
    - If user is logged in, login page redirects to home page
  - Implement logout button (for session)
