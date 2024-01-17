package com.example.backend_sem2.repository;

import com.example.backend_sem2.enums.MovieBookingStatusEnum;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MovieRepo extends JpaRepository<Movie, Long> {

//    @Query(value = "SELECT new Movie(m.id, m.movieName, m.posterUrl, m.duration, new Category , m.movieLabel) " +
//            "FROM Movie m LEFT JOIN FETCH m.categoryList c " +
//            "WHERE (cast(:partOfMovieName AS text) IS NULL OR lower(m.movieName) LIKE CONCAT('%', lower(cast(:partOfMovieName AS text)), '%')) " +
//            // ==> after query, return only desired "category"
////            "AND (:categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%')) " +
//
//            /*  return all "category" of the Movie which satisfy having desired "category"*/
//            "AND (m.id IN (SELECT DISTINCT m.id FROM Movie m LEFT JOIN m.categoryList c " +
//            "WHERE :categoryName IS NULL OR c.categoryName LIKE CONCAT('%', cast(:categoryName AS text), '%'))) " +
//
//            "AND (:movieLabel IS NULL OR m.movieLabel = :movieLabel) " +
//            "AND (:movieShowingStatusEnum IS NULL OR m.movieShowingStatusEnum = :movieShowingStatusEnum) " +
//            "AND (:movieBookingStatusEnum IS NULL OR m.movieBookingStatusEnum = :movieBookingStatusEnum) " +
//            "AND (:deleted IS NULL OR m.deleted = :deleted) ")
//    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName,
//                                            MovieLabelEnum movieLabel, MovieShowingStatusEnum movieShowingStatusEnum,
//                                            MovieBookingStatusEnum movieBookingStatusEnum, Boolean deleted);

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

    Page<Movie> getMoviesByOpeningTimeAfter(Pageable pageable, ZonedDateTime zonedDateTime);

    @Query(value = "FROM Movie m LEFT JOIN FETCH m.slotList s " +
            "WHERE s.id = :slotId")
    Optional<Movie> findMovieBySlotId(Long slotId);

    @Modifying
    @Query("UPDATE Movie m SET m.movieShowingStatusEnum = :showingStatusEnum " +
            "WHERE m.openingTime >= :startTime " +
            "AND (cast(:startTime as timestamp) IS NULL OR m.openingTime < :endTime)")
    void updateShowingStatusByOpeningTimeAfter(ZonedDateTime startTime, ZonedDateTime endTime, MovieShowingStatusEnum showingStatusEnum);

    @Transactional
    @Modifying
    @Query("UPDATE Movie m " +
            "SET m.movieBookingStatusEnum = :allowedStatus WHERE (m.openingTime <= :threeDayAfterToday AND :today <= m.closingTime)")
    void updateAllowedBookingStatus (ZonedDateTime today, ZonedDateTime threeDayAfterToday, MovieBookingStatusEnum allowedStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Movie m " +
            "SET m.movieBookingStatusEnum = :notAllowedStatus WHERE :today > m.closingTime")
    void updateNotAllowedBookingStatus (ZonedDateTime today, MovieBookingStatusEnum notAllowedStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Movie m " +
            "SET m.movieShowingStatusEnum = :nowShowingStatus WHERE :today >= m.openingTime AND :today <= m.closingTime")
    void updateNowShowingStatus (ZonedDateTime today, MovieShowingStatusEnum nowShowingStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Movie m " +
            "SET m.movieShowingStatusEnum = :endedStatus WHERE :today > m.closingTime")
    void updateEndedShowingStatus (ZonedDateTime today, MovieShowingStatusEnum endedStatus);

    @Query("FROM Movie m JOIN FETCH m.slotList " +
            "WHERE m.id = :movieId")
    Movie findByIdJoinFetchSlot(Long movieId);
}
