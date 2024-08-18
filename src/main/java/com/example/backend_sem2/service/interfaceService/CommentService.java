package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAllCommentByMovieName(String movieName);

    CommentResponse saveComment(HttpServletRequest request, CommentRequest commentRequest);
    CommentResponse saveComment(CommentRequest commentRequest);

    CommentResponse updateComment(CommentRequest commentRequest, Long id);

    List<CommentResponse> getAllCommentByMovieId(Long movieId);
}
