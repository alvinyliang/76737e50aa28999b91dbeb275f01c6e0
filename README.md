# Fabflix

## Project Dependencies
  - MySQL Connector/J 6.0.5
    - https://mvnrepository.com/artifact/mysql/mysql-connector-java/6.0.5
  - Tomcat v8.5
  
## To do:
  - Implement logout button (for session)
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
