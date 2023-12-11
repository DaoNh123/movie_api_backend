package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Movie extends BaseEntity{
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "poster_url", columnDefinition = "TEXT")
    private String posterUrl;
    @Column(name = "director")
    private String director;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    @Column(name = "opening_day", columnDefinition = "DATE")
    private LocalDate openingDay;
    @Column(name = "closing_day", columnDefinition = "DATE")
    private LocalDate closingDay;
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
    private List<Category> categoryList;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.DETACH,
                    CascadeType.REFRESH, CascadeType.MERGE
            }
    )
    private MovieLabel movieLabel;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "movie", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    private List<Comment> commentList;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "movie", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    private List<Slot> slotList;

    @Override
    public String toString() {
        return super.toString() + "Movie{" +
                "movieName='" + movieName + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", director='" + director + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", language='" + language + '\'' +
                ", openingDay=" + openingDay +
                ", closingDay=" + closingDay +
                '}';
    }
}
