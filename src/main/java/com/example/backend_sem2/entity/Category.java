package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SuperBuilder
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
    @Column(name = "genre_id")
    private Long genreId; // connect with "genreId" in theMovieDB

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Category category = (Category) o;

        return Objects.equals(categoryName.toLowerCase(), category.categoryName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return categoryName.toLowerCase().hashCode();
    }
}
