package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.TheaterRoomResponse;
import com.example.backend_sem2.entity.TheaterRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface TheaterRoomMapper {
    TheaterRoomResponse toDto(TheaterRoom theaterRoom);
}
