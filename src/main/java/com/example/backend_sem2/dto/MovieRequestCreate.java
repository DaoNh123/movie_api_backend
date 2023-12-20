package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Comment;
import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.entity.Slot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequestCreate {
    private String movieName;
    private String posterUrl;
    private String director;
    private String description;
    private Long duration;          // calculate in seconds
    private String language;

    private ZonedDateTime openingTime;      // The time which customer can book a ticket
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
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
    private List<Slot> slotList = new ArrayList<>();
}
