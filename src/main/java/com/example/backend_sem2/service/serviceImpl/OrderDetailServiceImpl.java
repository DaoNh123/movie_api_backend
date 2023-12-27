package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.entity.OrderDetail;
import com.example.backend_sem2.repository.OrderDetailRepo;
import com.example.backend_sem2.repository.SeatRepo;
import com.example.backend_sem2.service.interfaceService.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepo orderDetailRepo;
    private SeatRepo seatRepo;

    @Override
    public List<String> getAllOrderedSeatName(Long slotId) {
        List<OrderDetail> orderDetailsInSlot = orderDetailRepo.getOrderDetailByOrder_Slot_Id(slotId);
        return orderDetailsInSlot.stream()
                .map(orderDetail -> orderDetail.getSeat().getSeatName()).toList();

//        return seatRepo.getOrderedSeatNameByOrder_Slot_Id(slotId);
    }
}
