package com.example.backend_sem2.dto;

import com.example.backend_sem2.entity.Movie;
import com.sun.tools.javac.Main;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {
    @NotBlank
    private String commentUsername;
    @Min(value = 1)
    @Max(value = 5)
    private Long starRate;
    @NotBlank
    private String commentContent;
    @NotNull
    private Long movieId;
}
