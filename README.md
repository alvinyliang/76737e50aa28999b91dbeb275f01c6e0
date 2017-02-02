# Fabflix

## When cloning the project:
  - Ensure MySQL Service is running on local machine.
  - Configure WebContent/WEB-INF/db_config.properties
  
  - Add External Jar -> MySQL Connector/J 6.0.5 to project buid path
  - Create /lib directory under /WEB-INF and include the following files
    - mysql-connector-java-6.0.5 https://mvnrepository.com/artifact/mysql/mysql-connector-java/6.0.5
    - jstl-1.2 https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4
    - javax.json 1.0.4
  - Under Eclipse "Problems" tab, change project JRE path to match current Java Version. Current JRE is set to version _111.
  
## Project Dependencies
  - Tomcat v8.5
  
## To do:
  - Implement search
    - Implement substring matching and partial matching (SQL queries with LIKE and ILIKE)
  - Implement browse
  - Implement pagination for search and browse
    - Implement caching for search results
  - Implement movie and star info page
  - Implement shopping cart and check out
  - Implement order saving
  
## Completed:
  - Implemented login page
    - Implemented login post to servlet
  - Implemented Home page
  - Implemented authenciate user in session
  - Implemented session checking
    - If user is logged in, login page redirects to home page
  - Implement logout button (for session)
