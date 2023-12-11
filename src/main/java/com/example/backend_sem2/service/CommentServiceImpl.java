package com.example.backend_sem2.service;

import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;
import com.example.backend_sem2.mapper.CommentMapper;
import com.example.backend_sem2.repository.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private CommentRepo commentRepo;
    private CommentMapper commentMapper;

    @Override
    public List<CommentResponse> getAllCommentByMovieName(String movieName) {
        return commentRepo.getCommentByMovie_MovieName(movieName)
                .stream().map(movie -> commentMapper.toDto(movie)).collect(Collectors.toList());
    }
}
