package com.example.backend_sem2.repository;

import com.example.backend_sem2.enums.MovieBookingStatusEnum;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MovieRepo extends JpaRepository<Movie, Long> {
//    @Query(value = "FROM Movie m LEFT JOIN FETCH m.categoryList c " +
//            "WHERE (cast(:partOfMovieName AS text) IS NULL OR m.movieName LIKE CONCAT('%', cast(:partOfMovieName AS text), '%')) " +
//            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +
//            "AND (:movieLabel IS NULL OR cast(m.movieLabel AS text) = :movieLabel) ")
//    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel);

//    @Query(value = "FROM Movie m LEFT JOIN FETCH m.categoryList c " +
//            "WHERE (cast(:partOfMovieName AS text) IS NULL OR m.movieName LIKE CONCAT('%', cast(:partOfMovieName AS text), '%')) " +
//            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +
//            "AND (:movieLabel IS NULL OR m.movieLabel = :movieLabel) ")
//    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel);

    @Query(value = "FROM Movie m LEFT JOIN FETCH m.categoryList c " +
            "WHERE (cast(:partOfMovieName AS text) IS NULL OR lower(m.movieName) LIKE CONCAT('%', lower(cast(:partOfMovieName AS text)), '%')) " +
            // ==> after query, return only desired "category"
//            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +

            /*  return all "category" of the Movie which satisfy having desired "category"*/
            "AND (m.id IN (SELECT DISTINCT m.id FROM Movie m LEFT JOIN m.categoryList c " +
            "WHERE :categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%'))) " +

            "AND (:movieLabel IS NULL OR m.movieLabel = :movieLabel) " +
            "AND (:movieShowingStatusEnum IS NULL OR m.movieShowingStatusEnum = :movieShowingStatusEnum) " +
            "AND (:movieBookingStatusEnum IS NULL OR m.movieBookingStatusEnum = :movieBookingStatusEnum) " +
            "AND (:deleted IS NULL OR m.deleted = :deleted) ")
    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName,
                                                      MovieLabelEnum movieLabel, MovieShowingStatusEnum movieShowingStatusEnum,
                                                      MovieBookingStatusEnum movieBookingStatusEnum, Boolean deleted);

    @Query(value = "FROM Movie m LEFT JOIN FETCH m.commentList c " +
            "WHERE m.id = :id")
    Movie getMovieWithComments(Long id);

    boolean existsByImdbId(String imdbId);

    boolean existsByMovieNameIgnoreCase(String movieName);

    Page<Movie> getMoviesByOpeningTimeAfter(Pageable pageable, ZonedDateTime zonedDateTime);

    @Query(value = "FROM Movie m LEFT JOIN FETCH m.slotList s " +
            "WHERE s.id = :slotId")
    Optional<Movie> findMovieBySlotId(Long slotId);

    @Query(value = "SELECT m.theMovieDBId FROM Movie m")
    List<Long> getAllTheMovieDBIdOfAllMovies();


}
