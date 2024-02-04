package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.createNewSlot.CreateSlotRequest;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.SlotInOrderRes;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.entity.Slot;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, MovieMapper.class})
public abstract class SlotMapper {
    @Autowired
    protected ReferenceMapper referenceMapper;

//    Slot toEntity(Long id);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theaterRoom",
        expression = "java(slot.getTheaterRoom().getTheaterRoomName())")
    public abstract SlotInOrderRes toSlotInOrderRes (Slot slot);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theaterRoom",
            expression = "java(slot.getTheaterRoom().getTheaterRoomName())")
    public abstract SlotResponse toSlotResponse(Slot slot);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "movie",
            expression = "java(referenceMapper.map(createSlotRequest.getMovieId(), com.example.backend_sem2.entity.Movie.class))")
    @Mapping(target = "theaterRoom",
            expression = "java(referenceMapper.map(createSlotRequest.getTheaterRoomId(), com.example.backend_sem2.entity.TheaterRoom.class))")
    public abstract Slot toEntity(CreateSlotRequest createSlotRequest);
}