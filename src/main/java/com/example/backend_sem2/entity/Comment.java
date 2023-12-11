package com.example.backend_sem2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Column(name = "comment_username")
    private String commentUsername;
    @Column(name = "star_rate")
    private Long starRate;
    @Column(name = "comment_content", columnDefinition = "TEXT")
    private String commentContent;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    private Movie movie;

//    @Override
//    public String toString() {
//        return "Comment{" +
//                "commentUsername='" + commentUsername + '\'' +
//                ", starRate=" + starRate +
//                ", commentContent='" + commentContent + '\'' +
//                '}';
//    }
}
