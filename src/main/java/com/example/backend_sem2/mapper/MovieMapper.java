package com.example.backend_sem2.mapper;

import com.example.backend_sem2.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface MovieMapper {
    Movie toEntity(Long id);
}
