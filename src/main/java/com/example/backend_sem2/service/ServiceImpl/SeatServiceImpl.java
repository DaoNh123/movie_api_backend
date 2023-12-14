package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.Enum.Status;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Seat;
import com.example.backend_sem2.mapper.SeatMapper;
import com.example.backend_sem2.repository.SeatRepo;
import com.example.backend_sem2.service.interfaceService.OrderDetailService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    private SeatRepo seatRepo;
    private OrderDetailService orderDetailService;
    private SeatMapper seatMapper;
    @Override
    public List<SeatResponse> getAllSeatOfASlotWithStatus(Long id, Long slotId) {
        List<String> orderedSeatNameList = orderDetailService.getAllOrderedSeatName(slotId);
        List<Seat> seatsListInSlot = seatRepo.getSeatBySlotId(slotId);

        List<SeatResponse> seatResponseList = seatsListInSlot.stream()
                .map(seat -> {
                    SeatResponse seatResponse = seatMapper.toDto(seat);
                    if(orderedSeatNameList.contains(seat.getSeatName())){
                        seatResponse.setStatus(Status.BOOKED);
                    }else {
                        seatResponse.setStatus(Status.AVAILABLE);
                    }
                    return seatResponse;
                }).toList();
        return seatResponseList;
    }
}
