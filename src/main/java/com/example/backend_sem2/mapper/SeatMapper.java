package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.OrderResponseInfo.SeatResponseInOrderRes;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Seat;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, SeatClassMapper.class})

public interface SeatMapper {
//    Seat toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatClass", target = "seatClassResponse")
    @Mapping(source = "id", target = "seatId")
    SeatResponse toDto(Seat seat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "seatClass",
            expression = "java(seat.getSeatClass().getSeatClassName())")
    @Mapping(target = "price",
            expression = "java(seat.getSeatClass().getPrice())")
    SeatResponseInOrderRes toSeatInOrderRes (Seat seat);
}
