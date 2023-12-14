package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Seat;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, SeatClassMapper.class})
public interface SeatMapper {
    Seat toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "seatClass", target = "seatClassResponse")
    SeatResponse toDto(Seat seat);
}
