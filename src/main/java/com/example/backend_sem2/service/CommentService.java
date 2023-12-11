package com.example.backend_sem2.service;

import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAllCommentByMovieName(String movieName);
}
