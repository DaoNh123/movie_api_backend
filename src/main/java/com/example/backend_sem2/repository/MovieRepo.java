package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.MovieLabelEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MovieRepo extends JpaRepository<Movie, Long> {
//    @Query(value = "FROM Movie m INNER JOIN FETCH m.categoryList c " +
//            "WHERE (:partOfMovieName IS NULL OR lower(m.movieName) LIKE '%' || lower(:partOfMovieName) || '%') AND " +
//            "(:categoryName IS NULL OR lower(c.categoryName) = lower(:categoryName))" +
//            " AND (:movieLabel IS NULL OR lower(m.movieLabel) = lower(:movieLabel))")

    @Query(value = "FROM Movie m INNER JOIN FETCH m.categoryList c " +
            "WHERE (:partOfMovieName IS NULL OR m.movieName LIKE CONCAT('%', cast(:partOfMovieName AS text), '%')) " +
            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +
            "AND (:movieLabel IS NULL OR m.movieLabel = :movieLabel) ")
    Page<Movie> getMoviePageableByCondition3(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel);

//        @Query(value = "FROM Movie m INNER JOIN FETCH m.categoryList c " +
//            "WHERE (:partOfMovieName IS NULL OR LOWER(CAST(m.movieName AS text)) LIKE '%' || CAST(:partOfMovieName AS text) || '%') AND " +
//            "(:categoryName IS NULL OR LOWER(CAST(c.categoryName AS text)) = LOWER(CAST(:categoryName AS text)))" )
//    Page<Movie> getMoviePageableByCondition1(Pageable pageable, String partOfMovieName, String categoryName);

//    @Query(value = "FROM Movie m INNER JOIN FETCH m.categoryList c " +
//            "WHERE (:partOfMovieName IS NULL OR m.movieName LIKE CONCAT('%', cast(:partOfMovieName AS text), '%')) " +
//            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) ")
////            "AND (:movieLabel IS NULL OR cast(c.movieLabel as text) = cast(:movieLabel AS text), '%') ")
//    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName);

//    Page<Movie> findAll(Pageable pageable);

    public List<Movie> findMoviesByMovieLabel (MovieLabelEnum movieLabelEnum);
}
