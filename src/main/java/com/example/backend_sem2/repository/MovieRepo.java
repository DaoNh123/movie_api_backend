package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT m FROM Movie m INNER JOIN Category c " +
            "WHERE (:partOfMovieName IS NULL OR lower(m.movieName) LIKE '%' || lower(:partOfMovieName) || '%') AND " +
            "(lower(c.categoryName) = lower(:categoryName)) AND " +
            "(lower(m.movieLabel) = lower(:movieLabel))")
    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel);
}
