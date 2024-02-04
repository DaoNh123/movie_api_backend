package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.dto.CommentResponse;
import com.example.backend_sem2.entity.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, MovieMapper.class, UserMapper.class})

public abstract class CommentMapper {
    @Autowired
    ReferenceMapper referenceMapper;
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "movie",
            expression = "java(referenceMapper.map(commentRequest.getMovieId(), com.example.backend_sem2.entity.Movie.class))")
    public abstract Comment toEntity(CommentRequest commentRequest);

    @Mapping(source = "user", target = "userDto")
    public abstract CommentResponse toDto (Comment comment);

}
