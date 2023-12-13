package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.repository.OrderDetailRepo;
import com.example.backend_sem2.service.interfaceService.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepo orderDetailRepo;

    @Override
    public List<String> getAllOrderedSeatName(Long slotId) {
        return orderDetailRepo.getOrderedSeatNameByOrder_Slot_Id(slotId);
    }
}
