package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.service.interfaceService.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public CommentResponse saveComment(
            @RequestBody CommentRequest commentRequest
    ){
        return commentService.saveComment(commentRequest);
    }

    @PutMapping("/update/{id}")
    public CommentResponse updateComment(
            @RequestBody CommentRequest commentRequest,
            @PathVariable Long id
    ){
        return commentService.updateComment(commentRequest, id);
    }
}
