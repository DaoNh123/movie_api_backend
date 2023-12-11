package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.Movie;
import com.sun.tools.javac.Main;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    private String commentUsername;
    private Long starRate;
    private String commentContent;
    private Long movieId;
}
