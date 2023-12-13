package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.entity.Seat;
import com.example.backend_sem2.repository.SeatRepo;
import com.example.backend_sem2.service.interfaceService.OrderDetailService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    private SeatRepo seatRepo;
    private OrderDetailService orderDetailService;
    @Override
    public List<Seat> getAllSeatOfASlotWithStatus(Long id, Long slotId) {
        List<String> orderedSeatNameList = orderDetailService.getAllOrderedSeatName(slotId);
        List<Seat> seatsListInSlot = seatRepo.getSeatBySlotId(slotId);
        return null;
    }
}
