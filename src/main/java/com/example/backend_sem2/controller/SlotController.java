package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.service.interfaceService.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@AllArgsConstructor
public class SlotController {
    private SeatService seatService;

    @GetMapping("/{slot_id}")
    public List<SeatResponse> getAllSeatOfASlotWithStatus(
            @PathVariable(name = "slot_id") Long slotId
    ) {
        return seatService.getAllSeatOfASlotWithStatus(slotId);
    }
}
