package com.example.backend_sem2.controller.user;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.service.interfaceService.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/comments")
public class UserCommentController {
    private CommentService commentService;

    @PostMapping({"", "/"})
    public CommentResponse saveComment(
            HttpServletRequest request,
            @Valid @RequestBody CommentRequest commentRequest
    ){
        return commentService.saveComment(request ,commentRequest);
    }
}
