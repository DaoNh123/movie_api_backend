package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Comment;
import com.example.backend_sem2.entity.MovieLabelEnum;
import com.example.backend_sem2.entity.Slot;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class MovieResponse {
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
    @Column(name = "movie_label")
    @Enumerated(EnumType.STRING)
    private MovieLabelEnum movieLabel;
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
}
