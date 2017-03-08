DELIMITER $$
CREATE PROCEDURE add_movie (IN m_title VARCHAR(100), 
	IN m_year INT, 
	IN m_director VARCHAR(100), 
	IN star_first_name VARCHAR(50),
	IN star_last_name VARCHAR(50),
	IN genre_name VARCHAR(32))
BEGIN
	DECLARE m_id INT;
	DECLARE star_id INT;
	DECLARE genre_id INT;

	/* if star or genre match*/
	IF (
		((SELECT COUNT(*) FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name)) >= 1) OR 
		((SELECT COUNT(*) FROM moviedb.genres WHERE name = genre_name) >= 1)
		) THEN

		/* if movie not exist in db*/
		IF ((SELECT COUNT(*) FROM moviedb.movies WHERE title = m_title) = 0) THEN

			INSERT INTO moviedb.movies (title, year, director) VALUES(m_title, m_year, m_director);
			SELECT LAST_INSERT_ID() INTO m_id;
            
			SELECT "Inserted movie to movies table.";

			/* if star exist, add to stars_in_movies*/
			IF ((SELECT COUNT(*) FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name)) >= 1) THEN
				SELECT id INTO star_id FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name) LIMIT 1;
				INSERT INTO moviedb.stars_in_movies (star_id, movie_id) VALUES (star_id, m_id);

				SELECT "Added record to stars_in_movies table";

			END IF;

            /* if star not exist, add it*/
			IF ((SELECT COUNT(*) FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name)) = 0) THEN
				INSERT INTO moviedb.stars (first_name, last_name) VALUES (star_first_name, star_last_name);
				SELECT LAST_INSERT_ID() INTO star_id;
				INSERT INTO moviedb.stars_in_movies (star_id, movie_id) VALUES (star_id, m_id);

				SELECT "Adding new star to stars and stars_in_movies table";

			END IF;


			/* if genre exist, add to genre_in_movies */
			IF ((SELECT COUNT(*) FROM moviedb.genres WHERE name = genre_name) >= 1) THEN
				SELECT id INTO genre_id FROM moviedb.genres WHERE name = genre_name LIMIT 1;
				INSERT INTO moviedb.genres_in_movies (genre_id, movie_id) VALUES (genre_id, m_id);

				SELECT "Added record to genre_in_movies table";

			END IF;
			
            /* if genre not exist, add it*/
			IF ((SELECT COUNT(*) FROM moviedb.genres WHERE name = genre_name) = 0) THEN
				INSERT INTO moviedb.genres (name) VALUES (genre_name);
				SELECT LAST_INSERT_ID() INTO genre_id;
				INSERT INTO moviedb.genres_in_movies (genre_id, movie_id) VALUES (genre_id, m_id);
				SELECT "Adding new genre to genre and genres_in_movies table";

			END IF;






        ELSE
        	SELECT "Cannot add existing movie in db." ;
		END IF;

	ELSE
		/* record does not match star or genre*/
		/* check movie not exist*/
		IF ((SELECT COUNT(*) FROM moviedb.movies WHERE title = m_title) = 0) THEN
			INSERT INTO moviedb.movies (title, year, director) VALUES(m_title, m_year, m_director);
			SELECT LAST_INSERT_ID() INTO m_id;
			SELECT "Inserted movie to movies table.";

			/* if star not exist, create star*/
			IF ((SELECT COUNT(*) FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name)) = 0) THEN
				INSERT INTO moviedb.stars (first_name, last_name) VALUES (star_first_name, star_last_name);
				SELECT LAST_INSERT_ID() INTO star_id;
				INSERT INTO moviedb.stars_in_movies (star_id, movie_id) VALUES (star_id, m_id);

				SELECT "Adding new star to stars and stars_in_movies table";

			END IF;
			
			/* if star exist, add to stars_in_movies*/
			IF ((SELECT COUNT(*) FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name)) >= 1) THEN
				SELECT id INTO star_id FROM moviedb.stars WHERE CONCAT(first_name, " ", last_name) = CONCAT(star_first_name, " ", star_last_name) LIMIT 1;
				INSERT INTO moviedb.stars_in_movies (star_id, movie_id) VALUES (star_id, m_id);

				SELECT "Added record to stars_in_movies table";

			END IF;

			/* if genre not exist, create genre*/
			IF ((SELECT COUNT(*) FROM moviedb.genres WHERE name = genre_name) = 0) THEN
				INSERT INTO moviedb.genres (name) VALUES (genre_name);
				SELECT LAST_INSERT_ID() INTO genre_id;
				INSERT INTO moviedb.genres_in_movies (genre_id, movie_id) VALUES (genre_id, m_id);

				SELECT "Adding new genre to genre and genres_in_movies table";

			END IF;

			/* if genre exist, add to genre_in_movies */
			IF ((SELECT COUNT(*) FROM moviedb.genres WHERE name = genre_name) >= 1) THEN
				SELECT id INTO genre_id FROM moviedb.genres WHERE name = genre_name LIMIT 1;
				INSERT INTO moviedb.genres_in_movies (genre_id, movie_id) VALUES (genre_id, m_id);

				SELECT "Added record to genre_in_movies table";

			END IF;


        ELSE
        	SELECT "Cannot add existing movie in db."  ;  
		END IF;

	END IF;
    
END
$$
DELIMITER ;