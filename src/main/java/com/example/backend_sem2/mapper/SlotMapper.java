package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.OrderResponseInfo.SlotInOrderRes;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.entity.Slot;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, MovieMapper.class})
public interface SlotMapper {

//    Slot toEntity(Long id);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "slotId", source = "id")
    @Mapping(target = "theaterRoom",
        expression = "java(slot.getTheaterRoom().getTheaterRoomName())")
    SlotInOrderRes toSlotInOrderRes (Slot slot);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theaterRoom",
            expression = "java(slot.getTheaterRoom().getTheaterRoomName())")
    SlotResponse toSlotResponse(Slot slot);
}
