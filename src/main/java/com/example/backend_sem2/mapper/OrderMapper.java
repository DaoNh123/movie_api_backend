package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.OrderRequest;
import com.example.backend_sem2.dto.OrderRequestWithLoginAccount;
import com.example.backend_sem2.dto.orderResponseInfoOverview.OrderResponseOverview;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.OrderResponse;
import com.example.backend_sem2.entity.Order;
import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.entity.User;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.repository.SeatRepo;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, SlotMapper.class, OrderDetailMapper.class, UserMapper.class})
public abstract class OrderMapper {
    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    ReferenceMapper referenceMapper;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "user", target = "userDto")
    public abstract OrderResponse toDto (Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "slot", expression = "java(referenceMapper.map(orderRequest.getSlotId(), com.example.backend_sem2.entity.Slot.class))")
    @Mapping(source = "seatIdList", target = "orderDetailList", qualifiedByName = "seatIdListToOrderDetailList")
    @Mapping(source = "customerEmail", target = "customerEmail")
    public abstract Order toEntity(OrderRequest orderRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "slot", expression = "java(referenceMapper.map(orderRequest.getSlotId(), com.example.backend_sem2.entity.Slot.class))")
    @Mapping(source = "orderRequest.seatIdList", target = "orderDetailList", qualifiedByName = "seatIdListToOrderDetailList")
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "customerName", expression = "java(user.getFullName())")
    @Mapping(target = "customerAge", expression = "java(user.getAge().longValue())")
    @Mapping(target = "customerEmail", expression = "java(user.getEmail())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Order toEntity(OrderRequestWithLoginAccount orderRequest, User user);

    @Named("seatIdListToOrderDetailList")
    public List<OrderDetail> seatIdListToOrderDetailList(List<Long> seatIdList){
        List<OrderDetail> orderDetailList = seatIdList.stream()
                .map(id -> seatRepo.findById(id)
                        .orElseThrow(() -> new CustomErrorException(HttpStatus.BAD_REQUEST, "Seat is not exist!"))
                )
                .map(
                        seat -> OrderDetail.builder().seat(seat).build()
                ).collect(Collectors.toList());
        return orderDetailList;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "slot", target = "slot")
    @Mapping(source = "orderDetailList", target = "orderDetailList")
    public abstract OrderResponseOverview toDtoOverview(Order order);

}
