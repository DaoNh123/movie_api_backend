package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.entity.Slot;

import java.util.List;

public interface SlotService {
    List<Slot> getSlotsByMovie_Id(Long id);
}
