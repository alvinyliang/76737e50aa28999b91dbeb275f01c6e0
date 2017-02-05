# Fabflix

## When cloning the project:
  - Ensure MySQL Service is running on local machine.
  - Configure WebContent/WEB-INF/db_config.properties
  
  - Add External Jar -> MySQL Connector/J 6.0.5 to project buid path
  - Create /lib directory under /WEB-INF and include the following files
    - mysql-connector-java-6.0.5 https://mvnrepository.com/artifact/mysql/mysql-connector-java/6.0.5
    - jstl-1.2 https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4
    - javax.json 1.0.4 https://mvnrepository.com/artifact/org.glassfish/javax.json/1.0.4
  - Under Eclipse "Problems" tab, change project JRE path to match current Java Version. Current JRE is set to version _111.
  - Query to create the schema for the cart:
  
  create table carts (
	session_id varchar(50) not null,
    customer_id integer not null,
    movie_id integer not null,
    quantity integer,
    primary key (session_id, customer_id, movie_id),
    FOREIGN KEY (`customer_id`)
        REFERENCES `moviedb`.`customers` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION,
    FOREIGN KEY (`movie_id`)
        REFERENCES `moviedb`.`movies` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION
    );
  
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
