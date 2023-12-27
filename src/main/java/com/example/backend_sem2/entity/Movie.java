package com.example.backend_sem2.entity;


import com.example.backend_sem2.enums.MovieBookingStatusEnum;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class Movie extends BaseEntity{
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "imdb_id")
    private String imdbId;              // new
    @Column(name = "the_movie_db_id")
    private Long theMovieDBId;        // new
    @Column(name = "imdb_ratings")
    private Double imdbRatings;         // new
    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;
    @Column(name = "director")
    private String director;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    @Column(name = "opening_time")
    private ZonedDateTime openingTime;      // The time which customer can book a ticket
    @Column(name = "closing_time")
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
    @Column(name = "showing_status")
    @Enumerated(EnumType.STRING)
    private MovieShowingStatusEnum movieShowingStatusEnum;        // UNRELEASED, NOW_SHOWING, ENDED
    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private MovieBookingStatusEnum movieBookingStatusEnum;      // ALLOWED, NOT_ALLOWED
    private Boolean deleted;
    @Column(name = "iframe", columnDefinition = "TEXT")
    private String iframe;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    @JoinTable(
            name = "category_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> categoryList;
    @Column(name = "movie_label")
    @Enumerated(EnumType.STRING)
    private MovieLabelEnum movieLabel;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "movie", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    @JsonIgnore
    private List<Comment> commentList;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "movie", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    @JsonIgnore
    private List<Slot> slotList;

    @PrePersist
    public void prePersistMovie(){
        this.deleted = false;

        /*  Set up MovieShowingStatusEnum   */
        LocalDate today = LocalDate.now();
        LocalDate openingDate = this.openingTime.toLocalDate();
        LocalDate closingDate = this.closingTime.toLocalDate();
        if(today.isBefore(openingDate)){
            this.movieShowingStatusEnum = MovieShowingStatusEnum.UNRELEASED;
        }
        else if(today.isAfter(closingDate)){
            this.movieShowingStatusEnum = MovieShowingStatusEnum.ENDED;
        }else {
            this.movieShowingStatusEnum = MovieShowingStatusEnum.NOW_SHOWING;
        }

        /*  Set up MovieBookingStatusEnum   */
        if(today.isBefore(openingDate.minusDays(3))){
            this.movieBookingStatusEnum = MovieBookingStatusEnum.NOT_ALLOWED;
        }else this.movieBookingStatusEnum = MovieBookingStatusEnum.ALLOWED;
    }

    @Override
    public String toString() {
        return super.toString() + "Movie{" +
                "movieName='" + movieName + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", director='" + director + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", language='" + language + '\'' +
                ", movieLabel='" + movieLabel + '\'' +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                '}';
    }
}
