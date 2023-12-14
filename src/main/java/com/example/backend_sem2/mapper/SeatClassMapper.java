package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.SeatClassResponse;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Seat;
import com.example.backend_sem2.entity.SeatClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface SeatClassMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SeatClassResponse toDto(SeatClass seatClass);
}
