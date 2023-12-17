package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.OrderResponseInfo.OrderDetailInOrderRes;
import com.example.backend_sem2.entity.OrderDetail;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, SeatMapper.class})
public interface OrderDetailMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "orderDetailId", source = "id")
    @Mapping(target = "seat", source = "seat")
    OrderDetailInOrderRes toOrderDetailInOrderRes (OrderDetail orderDetail);
}
