package com.example.backend_sem2.repository;

import com.example.backend_sem2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    public List<Comment> getCommentByMovie_MovieName (String movieName);
}
