package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MovieRepo extends JpaRepository<Movie, Long> {
    @Query(value = "FROM Movie m INNER JOIN FETCH m.categoryList c " +
            "WHERE (cast(:partOfMovieName AS text) IS NULL OR m.movieName LIKE CONCAT('%', cast(:partOfMovieName AS text), '%')) " +
            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +
            "AND (:movieLabel IS NULL OR cast(m.movieLabel AS text) = :movieLabel) ")
    Page<Movie> getMoviePageableByCondition3(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel);

    @Query(value = "FROM Movie m INNER JOIN FETCH m.commentList c " +
            "WHERE m.id = :id")
    Movie getMovieWithComments(Long id);

    boolean existsByImdbId(String imdbId);
}
