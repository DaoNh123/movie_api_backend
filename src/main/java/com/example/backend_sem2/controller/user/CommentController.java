package com.example.backend_sem2.controller.user;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.service.interfaceService.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    @PostMapping({"", "/"})
    public CommentResponse saveComment(
           @Valid @RequestBody CommentRequest commentRequest
    ){
        return commentService.saveComment(commentRequest);
    }

    @PutMapping("/update/{id}")
    public CommentResponse updateComment(
            @Valid @RequestBody CommentRequest commentRequest,
            @PathVariable Long id
    ){
        return commentService.updateComment(commentRequest, id);
    }
}
