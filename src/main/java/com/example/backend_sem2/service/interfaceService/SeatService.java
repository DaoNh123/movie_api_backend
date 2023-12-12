package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.entity.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getAllSeatOfASlotWithStatus(Long id, Long slotId);
}
