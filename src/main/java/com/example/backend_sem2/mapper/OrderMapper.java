package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.OrderResponseInfo.OrderResponse;
import com.example.backend_sem2.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, SlotMapper.class, OrderDetailMapper.class})
public interface OrderMapper {

    OrderResponse toDto (Order order);
}
