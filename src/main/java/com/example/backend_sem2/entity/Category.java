package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString(callSuper = true)
@Table(name="categories")
public class Category extends BaseEntity{
    @Column(name = "category_name", unique = true)
    private String categoryName;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE
    })
    @JoinTable(
            name = "category_movie",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movieList;
}
