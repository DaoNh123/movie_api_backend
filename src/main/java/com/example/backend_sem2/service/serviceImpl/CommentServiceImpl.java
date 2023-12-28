package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;
import com.example.backend_sem2.mapper.CommentMapper;
import com.example.backend_sem2.repository.CommentRepo;
import com.example.backend_sem2.service.interfaceService.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepo commentRepo;
    private CommentMapper commentMapper;

    @Override
    public List<CommentResponse> getAllCommentByMovieName(String movieName) {
        return commentRepo.getCommentByMovie_MovieName(movieName)
                .stream().map(movie -> commentMapper.toDto(movie)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getAllCommentByMovieId(Long movieId) {
        return commentRepo.getCommentByMovie_Id(movieId)
                .stream().map(movie -> commentMapper.toDto(movie)).collect(Collectors.toList());

    }

    @Override
    public CommentResponse saveComment(CommentRequest commentRequest) {
        Comment savedComment = commentRepo.save(commentMapper.toEntity(commentRequest));
        return commentMapper.toDto(savedComment);
    }

    @Override
    public CommentResponse updateComment(CommentRequest commentRequest, Long id) {
        Comment updatedComment = commentRepo.save(commentMapper.toEntity(commentRequest));
        updatedComment.setId(id);
        return commentMapper.toDto(updatedComment);
    }
}
