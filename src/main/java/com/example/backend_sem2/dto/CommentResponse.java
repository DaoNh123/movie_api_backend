package com.example.backend_sem2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String commentUsername;
    private Long starRate;
    private String commentContent;
    private UserDto userDto;
}
