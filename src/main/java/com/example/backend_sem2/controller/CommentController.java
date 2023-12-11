package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;
import com.example.backend_sem2.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    @GetMapping("/{movieName}")
    public List<CommentResponse> getAllCommentByMovieName (
            @PathVariable String movieName
    ) {
        return commentService.getAllCommentByMovieName(movieName);
    }
}
