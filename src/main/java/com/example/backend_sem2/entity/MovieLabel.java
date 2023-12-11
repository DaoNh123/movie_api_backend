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
@Table(name="movie_labels")
public class MovieLabel extends BaseEntity{
    @Column(name = "movie_label_name", length = 10, unique = true)
    private String movieLabelName;
    @Column(name = "min_age")
    private Long minAge;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "movieLabel",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    private List<Movie> movieList;
}
